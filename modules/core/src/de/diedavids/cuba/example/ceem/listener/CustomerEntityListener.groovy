package de.diedavids.cuba.example.ceem.listener

import org.springframework.stereotype.Component
import com.haulmont.cuba.core.listener.AfterInsertEntityListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import javax.inject.Inject
import java.sql.Connection
import de.diedavids.cuba.example.ceem.entity.Customer

@Component("ceem_CustomerEntityListener")
public class CustomerEntityListener implements AfterInsertEntityListener<Customer> {

    @Inject
    RabbitTemplate rabbitTemplate


    @Override
    public void onAfterInsert(Customer entity, Connection connection) {
        rabbitTemplate.convertAndSend("Hello World")
    }

}