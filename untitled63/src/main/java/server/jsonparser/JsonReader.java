package server.jsonparser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.entity.Deposit;
import server.entity.ServerInfo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {
    public static ServerInfo readServerInfo(String path) {
        ServerInfo serverInfo = new ServerInfo();
        try {
            FileReader jsonFile = new FileReader("src\\main\\resources\\core.json");
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonFileObject = (JSONObject) jsonParser.parse(jsonFile);

            //Server
            // set port
            serverInfo.setPort(((Number) jsonFileObject.get("port")).intValue());
            //set outLog
            serverInfo.setOutLog((String) jsonFileObject.get("outLog"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverInfo;
    }

    public static List<Deposit> readDepositsInfo(String path) {
        List<Deposit> depositList = new ArrayList<Deposit>();
        try {
            FileReader jsonFile = new FileReader("src\\main\\resources\\core.json");
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonFileObject = (JSONObject) jsonParser.parse(jsonFile);

            //Deposits
            JSONArray depositArray = (JSONArray) jsonFileObject.get("deposits");
            for (int counter = 0; counter < depositArray.size(); counter++) {
                Deposit deposit = new Deposit();
                JSONObject depositObject = (JSONObject) depositArray.get(counter);
                //set customer
                String customer = (String) depositObject.get("customer");
                deposit.setCustomer(customer);
                //set id
                String id = (String) depositObject.get("id");
                deposit.setCustomerId(id);
                //set initial Balance
                String initialBalanceString = (String) depositObject.get("initialBalance");
                BigDecimal initialBalance = new BigDecimal(initialBalanceString.replaceAll(",", ""));
                deposit.setInitialBalance(initialBalance);
                //set upper Bound
                String upperBoundString = (String) depositObject.get("upperBound");
                BigDecimal upperBound = new BigDecimal(upperBoundString.replaceAll(",", ""));
                deposit.setUpperBound(upperBound);
                depositList.add(deposit);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return depositList;
    }

}




