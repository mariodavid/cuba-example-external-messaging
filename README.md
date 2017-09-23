# cuba-example-external-messaging
CUBA example that shows how to interact with a external messaging system. In this case it will be shown how to connect and interact with a RabbitMQ message broker.


In order to communicate with RabbitMQ, we will use the build in Spring integration called `spring-amqp` and `spring-rabbit`.

### 1. Install the dependencies

To make CUBA work with Spring AMQP, there needs to be a corresponding dependency in the build.gradle:

````

configure(coreModule) {

    // ...
    
    dependencies {
    
        // ...
        
        // add the spring rabbit integration to the core module
        
        compile 'org.springframework.amqp:spring-rabbit:1.7.4.RELEASE'
        compile 'org.springframework.amqp:spring-amqp:1.7.4.RELEASE'

    }
    
    // ...
    
}
````

### 2. Configure spring-amqp settings

After the libraries are included correctly, you can configure the communication channel to the RabbitMQ server with the appllication in the `spring.xml`:

````
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:rabbit="http://www.springframework.org/schema/rabbit"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.3.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd
            http://www.springframework.org/schema/rabbit
            https://raw.github.com/SpringSource/spring-amqp/master/spring-rabbit/src/main/resources/org/springframework/amqp/rabbit/config/spring-rabbit-1.7.xsd">

    <!-- Annotation-based beans -->
    <context:component-scan base-package="de.diedavids.cuba.example.ceem"/>


    <!-- configure connection to your RabbitMQ server -->
    
    <rabbit:connection-factory id="connectionFactory"
                               host="192.168.99.100"
    />


    <!-- define more configurations to the RabbitMQ configuration, like Queues etc. -->
    
    <rabbit:template id="amqpTemplate" connection-factory="connectionFactory"
                     exchange="myExchange" routing-key="foo.bar"/>

    <rabbit:admin connection-factory="connectionFactory" />

    <rabbit:queue name="myQueue" />

    <rabbit:topic-exchange name="myExchange">
        <rabbit:bindings>
            <rabbit:binding queue="myQueue" pattern="foo.*" />
        </rabbit:bindings>
    </rabbit:topic-exchange>



    <! -- define a class which acts as the consumer for messages -->
    
    
    <rabbit:listener-container connection-factory="connectionFactory">
        <rabbit:listener ref="bigOrderPresentCreator" method="receive" queue-names="myQueue" />
    </rabbit:listener-container>


    <bean id="bigOrderPresentCreator" class="de.diedavids.cuba.example.ceem.core.BigOrderPresentCreator" />
    
</beans>

````


### 3. Send messages to RabbitMQ

In this example I'll send a message to the Queue when an Order is created.
This can be done in several places, in this case we will use an Entity listener like this: 

````
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
````

To communicate with RabbitMQ, there is a RabbitTemplate bean from Spring, which will do the heavy lifting on
most of the stuff.

You can call `convertAndSend` with the Message you want to send (in this case I use a String).


### 4. Receive messages from RabbitMQ

To receive a Message from the RabbitMQ server, I created a class called `BigOrderPresentCreator` in the core module. 
It creates a Present in the system if the Order amount is greater than 100 bugs.

The connection to receiving the message is done via the spring.xml configuration file.

The class looks like this:

````
class BigOrderPresentCreator {


    @Inject
    Authentication authentication

    @Inject
    Metadata metadata

    @Inject
    DataManager dataManager


    void receive(String message) {


        authentication.begin()

        createPresentFromMessage(message)

        authentication.end()

    }

    private Present createPresentFromMessage(String message) {
        def (String customerName, Integer amount) = parseMessageDetails(message)

        if (amount > 100) {
            Present present = metadata.create(Present)
            present.customerName = customerName
            present.amount = amount

            dataManager.commit(present)
        }
    }

    private List parseMessageDetails(String message) {
        def messageParts = message.split(";")
        def customerName = messageParts[0]
        def amount = Integer.parseInt(messageParts[1])
        [customerName, amount]
    }
}

````


You can see the result in the Present browse screen. For every big order, a present will be created.
