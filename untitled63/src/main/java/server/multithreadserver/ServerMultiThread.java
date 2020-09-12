package server.multithreadserver;

import client.entity.Transaction;
import server.entity.Response;
import server.checkvalidation.CheckValidation;
import server.entity.Deposit;
import server.entity.ServerInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static server.jsonparser.JsonReader.readDepositsInfo;
import static server.jsonparser.JsonReader.readServerInfo;

public class ServerMultiThread extends Thread {
    private static final String jsonPath = "src\\main\\resources\\core.json";
    private static final Logger logger = Logger.getLogger(ServerMultiThread.class.getName());
    ServerInfo serverInfo;
    ObjectInputStream serverIn;
    ObjectOutputStream serverOut;
    Response serverResponse;
    Socket server;
    List<Deposit> depositList;

    public ServerMultiThread(Socket server, List<Deposit> depositList, ServerInfo serverInfo) throws IOException {
        this.server = server;
        this.depositList = depositList;
        this.serverInfo = serverInfo;
    }

    public ServerMultiThread() throws IOException {
        ServerInfo serverInfo = readServerInfo(jsonPath);
        List<Deposit> depositList = readDepositsInfo(jsonPath);

            FileHandler fileHandler = new FileHandler("src\\main\\resources\\core.json" + serverInfo.getOutLog());
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);
            fileHandler.setFormatter(simpleFormatter);


    }

    @Override
    public void run() {
        try {
            //recieve client req
            serverIn = new ObjectInputStream(server.getInputStream());
            Transaction transaction = (Transaction) serverIn.readObject();
            logger.info("recieve client request.\n Transaction Id=" + transaction.getTransactionId());

            //check req valid
            CheckValidation checkValidation = new CheckValidation();
            serverResponse = checkValidation.checkValidation(transaction, depositList);
            logger.info("check request validation of Terminal" + "\n Transaction Id =" + transaction.getTransactionId());

            //send response;
            serverOut = new ObjectOutputStream(server.getOutputStream());
            serverOut.writeObject(serverResponse);
            logger.info("sent response of Terminal " + serverResponse.getResponseId());
            server.close();

        } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            logger.warning("Socket timed out!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
