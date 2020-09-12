package server.entity;

import java.io.Serializable;
import java.util.List;

public class ServerInfo implements Serializable {
    private int port;
    private String outLog;
    private List<Deposit> deposits;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getOutLog() {
        return outLog;
    }

    public void setOutLog(String outLog) {
        this.outLog = outLog;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }
}
