package gov.saip.applicationservice.config;

import java.util.HashMap;
import java.util.Map;



import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;
import org.apache.activemq.artemis.core.remoting.impl.netty.TransportConstants;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;


@Slf4j
@EnableJms
@Configuration
@ConfigurationProperties(prefix = "spring.artemis")
public class JmsConfig {
    @Value("${spring.artemis.broker-url}")
    private String brokerUrl;
    @Value("${spring.artemis.user}")
    private String user;
    @Value("${spring.artemis.password}")
    protected String password;

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() throws JMSException {
        Map<String, Object> tcpParams = new HashMap<>();
        tcpParams.put(TransportConstants.SSL_ENABLED_PROP_NAME, false);
        TransportConfiguration transportConfiguration =
                new TransportConfiguration(NettyConnectorFactory.class.getName(), tcpParams);
        ActiveMQConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(false, transportConfiguration);
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUser(user);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }



    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setObjectMapper(objectMapper());
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Bean
    public JmsTemplate jmsTemplate() throws JMSException {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(activeMQConnectionFactory());
        template.setMessageConverter(messageConverter());
        template.setPubSubDomain(false);
        return template;
    }
}