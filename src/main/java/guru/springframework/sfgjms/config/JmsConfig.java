package guru.springframework.sfgjms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    public final static String SIMPLE_QUEUE_NAME = "jms-test-messaging-queue";
    public final static String SEND_RECV_QUEUE_NAME = "jms-send-receive-queue";
    public final static String TYPE_PROP_NAME = "_type";

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName(TYPE_PROP_NAME);
        return converter;
    }
}
