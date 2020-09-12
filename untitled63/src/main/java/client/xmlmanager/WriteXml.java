package client.xmlmanager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import server.entity.Response;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class WriteXml {
    public static void writeXml(Response serverResponse) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String outPutFilePath = "src\\main\\resources\\Response" + ".xml";
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document;
        File outPutFile = new File(outPutFilePath);
        Element rootElement;
        boolean fileExist = outPutFile.exists();
        if (!fileExist) {
            document = documentBuilder.newDocument();
            rootElement = document.createElement("Responses");
            document.appendChild(rootElement);
        } else {
            document = documentBuilder.parse(outPutFilePath);
            rootElement = document.getDocumentElement();
        }
        Element responseElement = document.createElement("Response");
        responseElement.setAttribute("id", serverResponse.getResponseId());
        responseElement.setAttribute("newBalance", serverResponse.getnewBalance());
        responseElement.setAttribute("Message", String.valueOf(serverResponse.getRsCode()));
        responseElement.setAttribute("TransactionType", serverResponse.getTransactionType());
        responseElement.setAttribute("CustomerId", serverResponse.getCustomerId());

        rootElement.appendChild(responseElement);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(outPutFile);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
    }
}
