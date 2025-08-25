package by.cizmar.internal.learn_rabbit_mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LearnRabbitMQ {

    public static void main(String[] args) {
        SpringApplication.run(LearnRabbitMQ.class, args);
    }

}
