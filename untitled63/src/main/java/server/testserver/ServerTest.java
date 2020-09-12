package server.testserver;

import server.multithreadserver.ServerMultiThread;
import server.entity.Deposit;
import server.entity.ServerInfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static server.jsonparser.JsonReader.readDepositsInfo;
import static server.jsonparser.JsonReader.readServerInfo;

public class ServerTest {
    private static final String jsonPath = "src\\main\\resources\\core.json";
    private static final Logger logger = Logger.getLogger(ServerMultiThread.class.getName());

    public static void main(String[] args) throws IOException {
        ServerInfo serverInfo = readServerInfo(jsonPath);
        int port = serverInfo.getPort();
        List<Deposit> depositList = readDepositsInfo(jsonPath);
        try {
            FileHandler fileHandler = new FileHandler("src\\main\\resources\\core.json" + serverInfo.getOutLog());
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);
            fileHandler.setFormatter(simpleFormatter);
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("Wating for client port" + serverSocket.getLocalPort());
                logger.info("Wating for client port" + serverSocket.getLocalPort());
                Socket socket = serverSocket.accept();
                Thread serverThread = new ServerMultiThread(socket, depositList, serverInfo);
                System.out.println("server just connected " + socket.getRemoteSocketAddress());
                logger.info("server just connected " + socket.getRemoteSocketAddress());
                serverThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.warning("IO Exception in ServerMultiThreadRunning Class.");
        }
    }
}
