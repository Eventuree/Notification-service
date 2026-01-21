package eventure.notificationservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.password-reset}")
    private String passwordResetQueue;

    @Value("${rabbitmq.routing-key.password-reset}")
    private String passwordResetRoutingKey;

    @Value("${rabbitmq.queue.user-registration}")
    private String userRegistrationQueue;

    @Value("${rabbitmq.routing-key.user-registration}")
    private String userRegistrationRoutingKey;

    @Value("${rabbitmq.queue.status-change}")
    private String statusChangeQueue;

    @Value("${rabbitmq.routing-key.status-change}")
    private String statusChangeRoutingKey;

    @Value("${rabbitmq.exchange.user-events}")
    private String userEventsExchange;

    private String dlxName = "user.events.dlx";

    @Bean
    public Queue passwordResetQueue() {
        return QueueBuilder.durable(passwordResetQueue)
                .withArgument("x-dead-letter-exchange", dlxName)
                .build();
    }

    @Bean
    public Binding passwordResetBinding() {
        return BindingBuilder
                .bind(passwordResetQueue())
                .to(userEventsExchange())
                .with(passwordResetRoutingKey);
    }

    @Bean Queue userRegistrationQueue() {
        return  QueueBuilder.durable(userRegistrationQueue).build();
    }

    @Bean
    public Binding userRegistrationBinding() {
        return BindingBuilder
                .bind(userRegistrationQueue()) // The queue bean you've defined
                .to(userEventsExchange())      // The TopicExchange bean
                .with(userRegistrationRoutingKey);
    }

    @Bean
    public Queue statusChangeQueue() {
        return QueueBuilder.durable(statusChangeQueue).build();
    }

    @Bean
    public TopicExchange userEventsExchange() {
        return new TopicExchange(userEventsExchange);
    }

    @Bean
    public Binding statusChangeBinding() {
        return BindingBuilder
                .bind(statusChangeQueue())
                .to(userEventsExchange())
                .with(statusChangeRoutingKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}