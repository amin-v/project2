package server.checkvalidation;

import exception.*;
import client.entity.Transaction;
import server.entity.Deposit;
import server.entity.Response;
import server.testserver.ServerSingleThread;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

public class CheckValidation {
    private static final Logger logger = Logger.getLogger(ServerSingleThread.class.getName());

    public static Response checkValidation(Transaction transaction, List<Deposit> depositList) {
        boolean checkCustomerExist = false;
        Response serverResponse = null;
        try {
            for (Deposit deposit : depositList) {
                if (deposit.getCustomerId().equals(transaction.getDepositId())) {
                    checkCustomerExist = true;
                    if (transaction.getTransactionType().equals("deposit")) {
                        BigDecimal newBalance = deposit.doDeposit(transaction);
                        System.out.println(newBalance);
                        serverResponse = new Response(transaction, newBalance);
                    } else if (transaction.getTransactionType().equals("withdraw")) {
                        BigDecimal newBalance = deposit.doWithdraw(transaction);
                        System.out.println(newBalance);
                        serverResponse = new Response(transaction, newBalance);
                    } else
                        throw new DepositTypeException();
                }
            }
            if (!checkCustomerExist) {
                throw new CustomerException();
            }
                } catch (InitialBalanceException e) {
                e.printStackTrace();
                logger.warning(e.getMessage());
              serverResponse = new Response(e.getMessage());
        } catch (UpperBoundException e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
            serverResponse = new Response(e.getMessage());
        } catch (DepositTypeException e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
            serverResponse = new Response(e.getMessage());
        } catch (CustomerException e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
            serverResponse = new Response(e.getMessage());
        }
        return serverResponse;
    }
}
