package server.entity;

import client.entity.Transaction;
import server.RsCode;

import java.io.Serializable;
import java.math.BigDecimal;

public class Response implements Serializable {
    private String responseId;
    private String newinitialBalance;
    private String rsCode;
    private String transactionType;
    private String customerId;
    int rsCode1;

    public Response() {
    }

    public Response(String rsCode) {
        this.rsCode = rsCode;
    }

    public Response(Transaction transaction, BigDecimal newBalance) {
        this.responseId = transaction.getTransactionId();
        this.newinitialBalance = String.valueOf(newBalance);
        this.rsCode = String.valueOf(RsCode.successful.getValue());
        this.transactionType = transaction.getTransactionType();
        this.customerId = transaction.getDepositId();
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getnewBalance() {
        return newinitialBalance;
    }

    public void setnewBalance(String newinitialBalance) {
        this.newinitialBalance = newinitialBalance;
    }

    public String getRsCode() {
        return rsCode;
    }

    public void setRsCode(String rsCode) {
        this.rsCode = rsCode;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getRsCode1() {
        return rsCode1;
    }

    public void setRsCode1(int rsCode1) {
        this.rsCode1 = rsCode1;
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseId='" + responseId + '\'' +
                ", newBalance='" + newinitialBalance + '\'' +
                ", rscode='" + rsCode + '\'' +
                ", transactionType='" + transactionType + '\'' +

                ", customerId='" + customerId + '\'' +
                '}';
    }
}
