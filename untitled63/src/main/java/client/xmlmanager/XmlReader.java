package client.xmlmanager;

import client.entity.Terminal;
import client.entity.Transaction;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class XmlReader {

    public static Terminal readXml(String path) {
        Terminal terminal = new Terminal();
        try {
            File xmlFile = new File(path);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            //document.getDocumentElement().normalize();

            //terminal........................
            NodeList nodeListTerminal = document.getElementsByTagName("terminal");
            Node nodeTerminal = nodeListTerminal.item(0);
            NamedNodeMap terminalAttrs = nodeTerminal.getAttributes();

            String terminalId = terminalAttrs.getNamedItem("id").getTextContent();
            terminal.setTerminalId(terminalId);

            String terminalType = terminalAttrs.getNamedItem("type").getTextContent();
            terminal.setTerminalType(terminalType);

            //server...........................
            NodeList nodeListServer = document.getElementsByTagName("server");
            Node nodeServer = nodeListServer.item(0);
            NamedNodeMap serverAttrs = nodeServer.getAttributes();

            String serverIP = serverAttrs.getNamedItem("ip").getTextContent();
            terminal.setServerIp(serverIP);

            int port = Integer.parseInt(serverAttrs.getNamedItem("port").getTextContent());
            terminal.setPort(port);

            //outLog...........................
            NodeList nodeListOutLog = document.getElementsByTagName("outLog");
            Node nodeOutLog = nodeListOutLog.item(0);
            NamedNodeMap outLogAttrs = nodeOutLog.getAttributes();

            String outLogPath = outLogAttrs.getNamedItem("path").getTextContent();
            terminal.setOutLogPath(outLogPath);

            //transaction.....................
            ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
            NodeList nodeListTransaction = document.getElementsByTagName("transaction");
            for (int counter = 0; counter < nodeListTransaction.getLength(); counter++) {
                Transaction transaction = new Transaction();

                Node nodeTransaction = nodeListTransaction.item(counter);
                NamedNodeMap transactionAttrs = nodeTransaction.getAttributes();

                String transactionId = transactionAttrs.getNamedItem("id").getTextContent();
                transaction.setTransactionId(transactionId);

                String transactionType = transactionAttrs.getNamedItem("type").getTextContent();
                transaction.setTransactionType(transactionType);

                BigDecimal transactionAmount = new BigDecimal((transactionAttrs.getNamedItem("amount").getTextContent().replaceAll(",", "")));
                transaction.setTransactionAmount(transactionAmount);

                String depositId = transactionAttrs.getNamedItem("deposit").getTextContent();
                transaction.setDepositId(depositId);

                //add to transaction list
                transactionList.add(transaction);
            }

            //set terminal transaction list.
            terminal.setTransactions(transactionList);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return terminal;
    }
}