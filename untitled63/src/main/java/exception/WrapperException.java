package exception;

public class WrapperException {
    public static String getMessage(Exception e) {
        if (e instanceof CustomerException) {
            return "Invalid Customer ID...";
        } else if (e instanceof DepositTypeException) {
            return "This deposit type is not define!";
        } else if (e instanceof InitialBalanceException) {
            return "wrong initialbalance";
        } else if (e instanceof UpperBoundException) {
            return "You pass the upperBound.";
        } else {
            e.printStackTrace();
            return "connect to support unit 1005";
        }
    }
}
