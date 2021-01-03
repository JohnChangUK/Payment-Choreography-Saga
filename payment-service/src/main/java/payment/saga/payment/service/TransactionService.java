package payment.saga.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import payment.saga.payment.model.Transaction;
import payment.saga.payment.repository.TransactionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final Scheduler jdbcScheduler;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              Scheduler jdbcScheduler) {
        this.transactionRepository = transactionRepository;
        this.jdbcScheduler = jdbcScheduler;
    }

    public Flux<Transaction> getAll() {
        return Flux.defer(
                () -> Flux.fromIterable(transactionRepository.findAll()))
                .subscribeOn(jdbcScheduler);
    }

    public Flux<List<Transaction>> reactiveGetAll() {
        Flux<Long> interval = Flux.interval(Duration.ofMillis(2000));
        Flux<List<Transaction>> transactionFlux = Flux.fromStream(
                Stream.generate(transactionRepository::findAll));
        return Flux.zip(interval, transactionFlux)
                .map(Tuple2::getT2);
    }

    public Mono<Transaction> getOrderById(Integer id) {
        return Mono.fromCallable(
                () -> transactionRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Transaction id: " + id + " does not exist")))
                .subscribeOn(jdbcScheduler);
    }

}
