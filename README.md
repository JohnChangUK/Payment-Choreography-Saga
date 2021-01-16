## [Choreography Saga Pattern](https://docs.microsoft.com/en-us/azure/architecture/reference-architectures/saga/saga) - Payment Microservices
### [Medium Article](https://medium.com/@johnchang94/choreography-saga-pattern-with-spring-cloud-kafka-ad46f01fc30a) - Explanation In Depth
### Implemented with 
- Spring Cloud Stream with Apache Kafka Binder
- Reactive Spring
- Using reactive programming to return `Flux` from the blocking JDBC API.

Operations persising to the database have a dedicated, well-tuned thread pool as it can isolate blocking IO calls separately from the application.

### System Design

<p>
    <img src="Choreography Saga.jpg" width="1200" height="600" />
</p>

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

#### Kafka Docker
- Once the Kafka Docker containers are running, go onto `localhost:9000` and create a cluster `Click 'Add Cluster'` with any name e.g. `payment-saga`.
- Under `Cluster Zookeeper Hosts` enter `zoo:2181`
### Topics
- There are 3 topics which the Order and Payment Services use; these must be created before starting both applications.
```
- orders
- payments
- transactions
```

#### Run
- Run the Order Service and the Payment Service application
- Make a POST Request to `localhost:9192/orders/create` with request body: 
```
{
    "userId": 1,
    "productId": 1
}
```
- Make a GET Request to `localhost:9192/orders/all` and see the order status updated

#### Data Flow
- The Order Service application takes in an `Order` as a request,
which creates and sends an `OrderPurchaseEvent` to the Kafka topic `orders` which is processed by `OrderPurchaseEventHandler` in the payment service.
- `OrderPurchaseEventHandler` processes the event and calculates if user has enough credit. If so,
it sets the generated `PaymentEvent` status to `APPROVED`, otherwise `DECLINED`.
- A `PaymentEvent` is emitted to the Kafka topic `payments`, which the `PaymentEventHandler` in the Payment Service application
listens for.
- If the `PaymentEvent` status is `APPROVED`, it saves the transaction in the `TransactionRepository`.
A `TransactionEvent` is emitted to the `transactions` topic.
- The `TransactionEventConsumer` reads this in the order service, if successful, the `OrderRepository` saves this as 
`ORDER_COMPLETED`, else `ORDER_FAILED`
