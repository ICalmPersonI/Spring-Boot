package antifraud.transaction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findAllByNumberAndTransactionDateBetween
            (String number, LocalDateTime fromDate, LocalDateTime toDate);

    List<Transaction> findAllByNumber(String number);
}
