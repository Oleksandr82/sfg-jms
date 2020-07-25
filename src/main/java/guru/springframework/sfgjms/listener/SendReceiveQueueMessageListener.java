package guru.springframework.sfgjms.listener;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SendReceiveQueueMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.SEND_RECV_QUEUE_NAME)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders messageHeaders,
                       Message message) throws JMSException {

        System.out.println("SendReceiveQueueMessageListener: Message is received: " + helloWorldMessage);

        HelloWorldMessage payloadMsg = HelloWorldMessage.builder()
                .uuid(UUID.randomUUID())
                .message("World!")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMsg);
    }
}
