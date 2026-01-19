package eventure.notificationservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.password-reset}")
    private String passwordResetQueue;

    private String dlxName = "user.events.dlx";

    @Bean
    public Queue passwordResetQueue() {
        return QueueBuilder.durable(passwordResetQueue)
                .withArgument("x-dead-letter-exchange", dlxName)
                .build();
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}