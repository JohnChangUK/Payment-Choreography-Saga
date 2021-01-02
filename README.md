## [Choreography Saga Pattern](https://docs.microsoft.com/en-us/azure/architecture/reference-architectures/saga/saga) - Payment Microservices

### Implemented with 
- Spring Cloud Stream with Apache Kafka Binder
- Reactive Spring

#### To start ZooKeeper and Kafka Brokers
```
cd docker
```

- #### Run 
```
docker-compose up
```

### Alternatively run Kafka locally by download binaries
#### Kafka Binary Distribution [Download](http://apachemirror.wuchna.com/kafka/2.3.1).

#### The Order Service application takes in an `Order` as a request,
creates and sends an `OrderPurchaseEvent` which is processed by `OrderPurchaseEventHandler`.
A `PaymentEvent` is emitted, which the `PaymentEventHandler` in the Payment Service application
listens for on the Kafka Queue, topic `payments`.
