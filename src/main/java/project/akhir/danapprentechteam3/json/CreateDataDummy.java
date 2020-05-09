package project.akhir.danapprentechteam3.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class CreateDataDummy implements Serializable
{
    public static void main(String[] args)
    {
            JSONObject customer1 = new JSONObject();
            customer1.put("id", "1");
            customer1.put("no.telp", "6285777488828");
            JSONObject customer2 = new JSONObject();
            customer2.put("id", "2");
            customer2.put("no.telp", "6285777488999");
            JSONObject customer3 = new JSONObject();
            customer3.put("id", "3");
            customer3.put("no.telp", "6285777488222");
            JSONObject customer4 = new JSONObject();
            customer4.put("id", "4");
            customer4.put("no.telp", "6285777488333");
            JSONObject customer5 = new JSONObject();
            customer5.put("id", "5");
            customer5.put("no.telp", "6285777488444");
            JSONObject customer6 = new JSONObject();
            customer6.put("id", "6");
            customer6.put("no.telp", "6285777488555");
            JSONObject customer7 = new JSONObject();
            customer7.put("id", "7");
            customer7.put("no.telp", "6285777488666");
            JSONObject customer8 = new JSONObject();
            customer8.put("id", "8");
            customer8.put("no.telp", "6285777488777");
            JSONObject customer9 = new JSONObject();
            customer9.put("id", "9");
            customer9.put("no.telp", "6285777488888");
            JSONObject customer10 = new JSONObject();
            customer10.put("id", "10");
            customer10.put("no.telp", "6285777488121");
            JSONObject customer11 = new JSONObject();
            customer11.put("id", "11");
            customer11.put("no.telp", "6285777488225");
            JSONObject customer12 = new JSONObject();
            customer12.put("id", "12");
            customer12.put("no.telp", "6285777488657");
            JSONObject customer13 = new JSONObject();
            customer13.put("id", "13");
            customer13.put("no.telp", "6285777488867");
            JSONObject customer14 = new JSONObject();
            customer14.put("id", "14");
            customer14.put("no.telp", "6285777488986");
            JSONObject customer15 = new JSONObject();
            customer15.put("id", "15");
            customer15.put("no.telp", "6285777488097");
            JSONArray list = new JSONArray();
            list.add(customer1);
            list.add(customer2);
            list.add(customer3);
            list.add(customer4);
            list.add(customer5);
            list.add(customer6);
            list.add(customer7);
            list.add(customer8);
            list.add(customer9);
            list.add(customer10);
            list.add(customer11);
            list.add(customer12);
            list.add(customer13);
            list.add(customer14);
            list.add(customer15);

            JSONObject allCustomer = new JSONObject();
            allCustomer.put("providerIndosat", list);

            try (FileWriter file = new FileWriter("/Users/setiawanaa/IdeaProjects/danapprentech-team3/src/main/resources/indosat/customerIndosat.json")) {
                file.write(allCustomer.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // -------XL-----

        JSONObject xlCustomer1 = new JSONObject();
        xlCustomer1.put("id", "1");
        xlCustomer1.put("no.telp", "6287667488828");
        JSONObject xlCustomer2 = new JSONObject();
        xlCustomer2.put("id", "2");
        xlCustomer2.put("no.telp", "628788488999");
        JSONObject xlCustomer3 = new JSONObject();
        xlCustomer3.put("id", "3");
        xlCustomer3.put("no.telp", "6287097488222");
        JSONObject xlCustomer4 = new JSONObject();
        xlCustomer4.put("id", "4");
        xlCustomer4.put("no.telp", "628765488333");
        JSONObject xlCustomer5 = new JSONObject();
        xlCustomer5.put("id", "5");
        xlCustomer5.put("no.telp", "6287897488444");
        JSONObject xlCustomer6 = new JSONObject();
        xlCustomer6.put("id", "6");
        xlCustomer6.put("no.telp", "6287967488555");
        JSONObject xlCustomer7 = new JSONObject();
        xlCustomer7.put("id", "7");
        xlCustomer7.put("no.telp", "6287657488666");
        JSONObject xlCustomer8 = new JSONObject();
        xlCustomer8.put("id", "8");
        xlCustomer8.put("no.telp", "6287657488777");
        JSONObject xlCustomer9 = new JSONObject();
        xlCustomer9.put("id", "9");
        xlCustomer9.put("no.telp", "6287887488888");
        JSONObject xlCustomer10 = new JSONObject();
        xlCustomer10.put("id", "10");
        xlCustomer10.put("no.telp", "6287657488121");
        JSONObject xlCustomer11 = new JSONObject();
        xlCustomer11.put("id", "11");
        xlCustomer11.put("no.telp", "6287097488225");
        JSONObject xlCustomer12 = new JSONObject();
        xlCustomer12.put("id", "12");
        xlCustomer12.put("no.telp", "6287567488657");
        JSONObject xlCustomer13 = new JSONObject();
        xlCustomer13.put("id", "13");
        xlCustomer13.put("no.telp", "6287507488867");
        JSONObject xlCustomer14 = new JSONObject();
        xlCustomer14.put("id", "14");
        xlCustomer14.put("no.telp", "6287667488986");
        JSONObject xlCustomer15 = new JSONObject();
        xlCustomer15.put("id", "15");
        xlCustomer15.put("no.telp", "6287097488097");
        JSONArray xlList = new JSONArray();
        xlList.add(xlCustomer1);
        xlList.add(xlCustomer2);
        xlList.add(xlCustomer3);
        xlList.add(xlCustomer4);
        xlList.add(xlCustomer5);
        xlList.add(xlCustomer6);
        xlList.add(xlCustomer7);
        xlList.add(xlCustomer8);
        xlList.add(xlCustomer9);
        xlList.add(xlCustomer10);
        xlList.add(xlCustomer11);
        xlList.add(xlCustomer12);
        xlList.add(xlCustomer13);
        xlList.add(xlCustomer14);
        xlList.add(xlCustomer15);

        JSONObject allXlCustomer = new JSONObject();
        allXlCustomer.put("providerIndosat", xlList);

        try (FileWriter file = new FileWriter("/Users/setiawanaa/IdeaProjects/danapprentech-team3/src/main/resources/xl/customerXl.json")) {
            file.write(allXlCustomer.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
