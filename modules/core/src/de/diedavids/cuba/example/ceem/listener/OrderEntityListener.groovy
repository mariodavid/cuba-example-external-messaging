package de.diedavids.cuba.example.ceem.listener

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import com.haulmont.cuba.core.listener.AfterInsertEntityListener

import javax.inject.Inject
import java.sql.Connection
import de.diedavids.cuba.example.ceem.entity.Order

@Component("ceem_OrderEntityListener")
public class OrderEntityListener implements AfterInsertEntityListener<Order> {


    @Inject
    RabbitTemplate rabbitTemplate

    @Override
    public void onAfterInsert(Order entity, Connection connection) {
        def message = entity.customer.name + ";" + entity.amount

        rabbitTemplate.convertAndSend(message)
    }

}