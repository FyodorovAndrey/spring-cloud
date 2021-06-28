package com.nvfredy.deposit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvfredy.deposit.dto.AccountResponseDTO;
import com.nvfredy.deposit.dto.BillRequestDTO;
import com.nvfredy.deposit.dto.BillResponseDTO;
import com.nvfredy.deposit.dto.DepositResponseDTO;
import com.nvfredy.deposit.entity.Deposit;
import com.nvfredy.deposit.exception.CommonDepositException;
import com.nvfredy.deposit.exception.DepositNotFoundException;
import com.nvfredy.deposit.repository.DepositRepository;
import com.nvfredy.deposit.rest.AccountServiceClient;
import com.nvfredy.deposit.rest.BillServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DepositService {

    private static final String EXCHANGE_DEPOSIT = "deposit.notify.exchange";
    private static final String ROUTING_KEY_DEPOSIT = "deposit.routing.key";

    private final DepositRepository depositRepository;
    private final AccountServiceClient accountServiceClient;
    private final BillServiceClient billServiceClient;
    private RabbitTemplate rabbitTemplate;

    public DepositService(DepositRepository depositRepository,
                          AccountServiceClient accountServiceClient,
                          BillServiceClient billServiceClient,
                          RabbitTemplate rabbitTemplate) {
        this.depositRepository = depositRepository;
        this.accountServiceClient = accountServiceClient;
        this.billServiceClient = billServiceClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public DepositResponseDTO deposit(Long accountId, Long billId, BigDecimal amount) {
        if (ObjectUtils.isEmpty(accountId) && ObjectUtils.isEmpty(billId)) {
            throw new CommonDepositException("Deposit is not possible: AccountID and BillID are null");
        }

        if (!ObjectUtils.isEmpty(billId)) {
            BillResponseDTO billResponseDTO = billServiceClient.getBillById(billId);
            BillRequestDTO billRequestDTO = createBillRequest(amount, billResponseDTO);

            billServiceClient.update(billId, billRequestDTO);

            AccountResponseDTO accountResponseDTO = accountServiceClient.getAccountById(billResponseDTO.getAccountId());
            depositRepository.save(new Deposit(amount, billId, OffsetDateTime.now(), accountResponseDTO.getEmail()));

            return getDepositResponseDTOAndSendToRabbitMQ(amount, accountResponseDTO);

        }
        BillResponseDTO defaultBill = getDefaultBill(accountId);
        BillRequestDTO billRequest = createBillRequest(amount, defaultBill);

        billServiceClient.update(defaultBill.getBillId(), billRequest);
        AccountResponseDTO account = accountServiceClient.getAccountById(accountId);

        depositRepository.save(new Deposit(amount, defaultBill.getBillId(), OffsetDateTime.now(), account.getEmail()));

        return getDepositResponseDTOAndSendToRabbitMQ(amount, account);
    }

    private DepositResponseDTO getDepositResponseDTOAndSendToRabbitMQ(BigDecimal amount, AccountResponseDTO accountResponseDTO) {
        DepositResponseDTO depositResponseDTO = new DepositResponseDTO(amount, accountResponseDTO.getEmail());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            rabbitTemplate.convertAndSend(EXCHANGE_DEPOSIT, ROUTING_KEY_DEPOSIT, objectMapper.writeValueAsString(depositResponseDTO));
        } catch (JsonProcessingException e) {
            log.error("Error with sending DepositResponseDTO to RabbitMQ.");
        }
        return depositResponseDTO;
    }

    private BillRequestDTO createBillRequest(BigDecimal amount, BillResponseDTO billResponseDTO) {
        BillRequestDTO billRequestDTO = new BillRequestDTO();
        billRequestDTO.setAccountId(billResponseDTO.getAccountId());
        billRequestDTO.setAmount(billResponseDTO.getAmount().add(amount));
        billRequestDTO.setIsDefault(billResponseDTO.getIsDefault());
        billRequestDTO.setOverdraftEnabled(billResponseDTO.getOverdraftEnabled());
        billRequestDTO.setCreationDate(billResponseDTO.getCreationDate());
        return billRequestDTO;
    }

    private BillResponseDTO getDefaultBill(Long accountId) {
        return billServiceClient
                .getBillsByAccountId(accountId)
                .stream()
                .filter(BillResponseDTO::getIsDefault)
                .findAny()
                .orElseThrow(() -> new CommonDepositException("Unable to find bill with accountID: " + accountId));
    }
}
