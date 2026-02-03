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

    @Value("${rabbitmq.queue.status-change}")
    private String statusChangeQueue;

    @Value("${rabbitmq.queue.rating-reminder}")
    private String ratingReminderQueue;

    @Value("${rabbitmq.routing-key.status-change}")
    private String statusChangeRoutingKey;

    @Value("${rabbitmq.routing-key.rating-reminder}")
    private String ratingReminderRoutingKey;

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
    public Queue statusChangeQueue() {
        return QueueBuilder.durable(statusChangeQueue).build();
    }

    @Bean
    public Queue ratingReminderQueue() {
        return QueueBuilder.durable(ratingReminderQueue).build();
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
    public Binding ratingReminderBinding() {
        return BindingBuilder
                .bind(ratingReminderQueue())
                .to(userEventsExchange())
                .with(ratingReminderRoutingKey);
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