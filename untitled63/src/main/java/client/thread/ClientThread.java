package client.thread;

import client.entity.Terminal;
import client.entity.Transaction;
import org.xml.sax.SAXException;
import server.entity.Response;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.*;

import static client.xmlmanager.WriteXml.writeXml;
import static client.xmlmanager.XmlReader.readXml;

public class ClientThread extends Thread {
    private static final Logger logger = Logger.getLogger(ClientThread.class.getName());
    Terminal terminal;

    public ClientThread(String inputFilePath) throws IOException {
        terminal = readXml(inputFilePath);
        Handler fileHandler = new FileHandler("src\\main\\resources\\terminal.xml" + terminal.getOutLogPath());
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        logger.addHandler(fileHandler);
        logger.setLevel(Level.INFO);
        fileHandler.setFormatter(simpleFormatter);
    }

    @Override
    public void run() {
        logger.info("start terminal" + terminal.getTerminalId());
        String ipAddress = terminal.getServerIp();
        int port = terminal.getPort();
        for (Transaction transaction : terminal.getTransactions()) {
            logger.info("connect to terminal: " + ipAddress + " on port: " + port);
            try {
                Socket clientSocket = new Socket(ipAddress, port);
                logger.info("connected to:" + clientSocket.getRemoteSocketAddress());

                ObjectOutputStream clientOutPut = new ObjectOutputStream(clientSocket.getOutputStream());
                clientOutPut.writeObject(transaction);
                logger.info("send transaction" + transaction.getTransactionId() + "to server");

                //receive
                ObjectInputStream clientInput = new ObjectInputStream(clientSocket.getInputStream());
                Response responseServer = (Response) clientInput.readObject();
                System.out.println("Server" + responseServer);
                logger.info("Responce of transaction" + transaction.getTransactionId() + "is receive and server say: " + responseServer.getRsCode());
                writeXml(responseServer);
                clientSocket.close();
            } catch (IOException | ClassNotFoundException | ParserConfigurationException | SAXException | TransformerException e) {
                e.printStackTrace();
            }
        }


    }
}
