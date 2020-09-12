package server.testserver;

import client.entity.Transaction;
import server.entity.Response;
import server.checkvalidation.CheckValidation;
import server.entity.Deposit;
import server.entity.ServerInfo;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


import static server.jsonparser.JsonReader.readDepositsInfo;
import static server.jsonparser.JsonReader.readServerInfo;


public class ServerSingleThread {
    public static final String path = "src\\resources\\core.json";
    public static Response serverResponse;
    private static final Logger logger = Logger.getLogger(ServerSingleThread.class.getName());

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket;
        ServerInfo serverInfo = readServerInfo(path);
        int port = serverInfo.getPort();
        List<Deposit> depositList = readDepositsInfo(path);
        Response response = new Response();
        ObjectInputStream serverIn;
        ObjectOutputStream serverOut;

        FileHandler fileHandler = new FileHandler("src\\main\\resources" + serverInfo.getOutLog());
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        logger.addHandler(fileHandler);
        logger.setLevel(Level.INFO);
        fileHandler.setFormatter(simpleFormatter);

        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                logger.info("Waiting for client port" + serverSocket.getLocalPort());
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                //get connection
                logger.info("server connected " + server.getRemoteSocketAddress());
                System.out.println(" connected to " + server.getRemoteSocketAddress());

                //receive client request
                serverIn = new ObjectInputStream(server.getInputStream());
                Transaction transaction = (Transaction) serverIn.readObject();
                logger.info("recieve client request.\n Transaction Id=" + transaction.getTransactionId());

                if (transaction == null)
                    break;

                //CheckValidation the request
                CheckValidation checkValidation = new CheckValidation();
                serverResponse = CheckValidation.checkValidation(transaction, depositList);
                //send response
                serverOut = new ObjectOutputStream(server.getOutputStream());
                serverOut.writeObject(serverResponse);
                server.close();

            }
        } catch (IOException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}

