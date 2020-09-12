package server.entity;
import exception.*;
import client.entity.Transaction;
import exception.UpperBoundException;
import java.io.Serializable;
import java.math.BigDecimal;

public class Deposit implements Serializable {
    private String customer;
    private String customerId;
    private BigDecimal initialBalance;
    private BigDecimal upperBound;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public BigDecimal getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(BigDecimal upperBound) {
        this.upperBound = upperBound;
    }


    public synchronized BigDecimal doDeposit(Transaction transaction) throws UpperBoundException, InitialBalanceException {
        BigDecimal newInitialBalance = getInitialBalance().add(transaction.getTransactionAmount());
        if (newInitialBalance.compareTo(upperBound) <= 0) {
            this.initialBalance = newInitialBalance;
        }
        if (newInitialBalance.compareTo(upperBound) > 0){
            throw new UpperBoundException();
        }
        return initialBalance;
    }

   public synchronized BigDecimal doWithdraw(Transaction transaction) throws UpperBoundException, InitialBalanceException {
        BigDecimal newInitialBalance = getInitialBalance().add(transaction.getTransactionAmount());
       if (newInitialBalance.compareTo(upperBound) >= 0) {
           this.initialBalance = newInitialBalance;
        }
       if (initialBalance.compareTo(transaction.getTransactionAmount()) < 0){
           throw new InitialBalanceException();
       }
        return initialBalance;
   }

    @Override
    public String toString() {
        return "Deposit{" +
                "customer='" + customer + '\'' +
                ", customerId='" + customerId + '\'' +
                ", initialBalance=" + initialBalance +
                ", upperBound=" + upperBound +
                '}';
    }
}
