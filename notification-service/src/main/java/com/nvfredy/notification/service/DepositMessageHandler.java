package com.nvfredy.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvfredy.notification.config.RabbitMQConfig;
import com.nvfredy.notification.dto.DepositResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DepositMessageHandler {

    private final JavaMailSender javaMailSender;

    public DepositMessageHandler(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DEPOSIT)
    public void receive(Message message) {
        log.info(String.valueOf(message));
        byte[] body = message.getBody();
        String jsonBody = new String(body);

        ObjectMapper objectMapper = new ObjectMapper();
        DepositResponseDTO responseDTO = null;
        try {
            responseDTO = objectMapper.readValue(jsonBody, DepositResponseDTO.class);
        } catch (JsonProcessingException e) {
            log.error("Error with reading DepositResponseDTO");
        }
        log.info(String.valueOf(responseDTO));

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(responseDTO.getMail());
        mailMessage.setFrom("my-mail@mail.com");

        mailMessage.setSubject("Mail from deposit");
        mailMessage.setText("Make deposit, sum: " + responseDTO.getAmount());

        try {
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }

    }
}
