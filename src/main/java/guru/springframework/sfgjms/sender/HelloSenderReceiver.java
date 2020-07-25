package guru.springframework.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

import static guru.springframework.sfgjms.config.JmsConfig.TYPE_PROP_NAME;

@Component
@RequiredArgsConstructor
public class HelloSenderReceiver {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {
        System.out.println("HelloSenderReceiver: Sending a message...");

        HelloWorldMessage message = HelloWorldMessage.builder()
                .uuid(UUID.randomUUID())
                .message("Hello!")
                .build();

        Message receivedMessage = jmsTemplate.sendAndReceive(JmsConfig.SEND_RECV_QUEUE_NAME, session -> {
            Message helloMessage = null;
            try {
                helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
            } catch (JsonProcessingException e) {
                //e.printStackTrace();
                throw new JMSException(e.getMessage());
            }
            helloMessage.setStringProperty(TYPE_PROP_NAME, HelloWorldMessage.class.getName());
            return helloMessage;
        });

        System.out.println("HelloSenderReceiver: Message is sent and response is received: "
                + receivedMessage.getBody(String.class));
    }
}
