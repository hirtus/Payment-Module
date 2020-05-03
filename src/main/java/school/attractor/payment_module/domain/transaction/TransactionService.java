package school.attractor.payment_module.domain.transaction;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {
    private TransactionRepository transactionRepository;


    public Transaction save(TransactionDTO transactionDTO) {
        Transaction transaction = Transaction.from(transactionDTO);
        transactionRepository.save(transaction);
        return transaction;
    }


    public Page<Transaction> getTransactions(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    public Transaction getTransaction(Integer transactionId) {
        return transactionRepository.findById ( transactionId ).orElse ( new Transaction () );
    }

    public void change(Transaction transaction) { transactionRepository.save ( transaction); }

    public Page<Transaction> searchTransactions(TransactionSearchDTO search, Pageable pageable) {
        Page<Transaction> transactions;

        if (search.getId().isEmpty() && search.getStatus().isEmpty() && !search.getShopName().isEmpty()) {
            transactions = transactionRepository.findAllByShopName(search.getShopName(), pageable);
        } else if (search.getId().isEmpty() && !search.getStatus().isEmpty() && search.getShopName().isEmpty()) {
            transactions = transactionRepository.findAllByStatus(search.getStatus(), pageable);
        } else if (!search.getId().isEmpty() && search.getStatus().isEmpty() && search.getShopName().isEmpty()) {
            transactions = transactionRepository.findAllByOrderId(search.getId(), pageable);
        } else if (search.getId().isEmpty() && !search.getStatus().isEmpty() && !search.getShopName().isEmpty()) {
            transactions = transactionRepository.findAllByStatusAndShopName(search.getStatus(), search.getShopName(), pageable);
        } else if (!search.getId().isEmpty() && search.getStatus().isEmpty() && !search.getShopName().isEmpty()) {
            transactions = transactionRepository.findAllByOrderIdAndShopName(search.getId(), search.getShopName(), pageable);
        } else if (!search.getId().isEmpty() && !search.getStatus().isEmpty() && search.getShopName().isEmpty()) {
            transactions = transactionRepository.findAllByOrderIdAndStatus(search.getId(), search.getStatus(), pageable);
        } else {
            transactions = transactionRepository.findAllByOrderIdAndStatusAndShopName(search.getId(), search.getStatus(), search.getShopName(), pageable);
        }

        return transactions;
    }

    public int  getSum(String orderID) {
        return transactionRepository.getSum(orderID);
    }

    public List<Transaction> getByOrderId(String orderId) {
       return transactionRepository.findAllByOrderId(orderId);
    }
}