package client.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Transaction implements Serializable {
    private String transactionId;
    private String transactionType;
    private BigDecimal transactionAmount;
    private String depositId;



    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", transactionAmount=" + transactionAmount +
                ", depositId='" + depositId + '\'' +
                '}';
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getDepositId() {
        return depositId;
    }

    public void setDepositId(String depositId) {
        this.depositId = depositId;
    }
}
