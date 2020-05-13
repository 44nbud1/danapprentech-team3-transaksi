package project.akhir.danapprentechteam3.paketdata.service;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class PaketData
{
    public String dataPaketIm3() throws FileNotFoundException {
        // creating JSONObject
        JSONObject jo = new JSONObject();

        // for address data, first create LinkedHashMap
        Map m = new LinkedHashMap(4);

        // for phone numbers, first create JSONArray  
        JSONArray ja = new JSONArray();

        m = new LinkedHashMap(4);
        m.put("nama_provider", "im3");
        m.put("harga", "27000");
        m.put("id", "1");
        m.put("paket_data", "Paket-Internet-1GB");

        // adding map to list 
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Indosat");
        m.put("harga", "32000");
        m.put("id", "2");
        m.put("paket_data", "Paket-OMG-2GB");

        // adding map to list 
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Indosat");
        m.put("harga", "39000");
        m.put("id", "3");
        m.put("paket_data", "Paket-OMG-4GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Indosat");
        m.put("harga", "63000");
        m.put("id", "4");
        m.put("paket_data", "Paket-OMG-6GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Indosat");
        m.put("harga", "89000");
        m.put("id", "5");
        m.put("paket_data", "Paket-OMG-8GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Indosat");
        m.put("harga", "25000");
        m.put("id", "6");
        m.put("paket_data", "Freedom-Combo-4GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Indosat");
        m.put("harga", "35000");
        m.put("id", "7");
        m.put("paket_data", "Freedom-Combo-6GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Indosat");
        m.put("harga", "45000");
        m.put("id", "8");
        m.put("paket_data", "Freedom-Combo-8GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Indosat");
        m.put("harga", "65000");
        m.put("id", "9");
        m.put("paket_data", "Freedom-Combo-10GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Indosat");
        m.put("harga", "80000");
        m.put("id", "10");
        m.put("paket_data", "Freedom-Combo-12GB");

        // adding map to list
        ja.add(m);

        // putting phoneNumbers to JSONObject 
        jo.put("paketData", ja);

        // writing JSON to file:"JSONExample.json" in cwd
        return (jo.toJSONString());
    }

    public String dataPaketXl() throws FileNotFoundException {
        // creating JSONObject
        JSONObject jo = new JSONObject();

        // for address data, first create LinkedHashMap
        Map m = new LinkedHashMap(4);

        // for phone numbers, first create JSONArray
        JSONArray ja = new JSONArray();

        m = new LinkedHashMap(4);
        m.put("nama_provider", "XL");
        m.put("harga", "27000");
        m.put("id", "1");
        m.put("paket_data", "Paket-Internet-1GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "XL");
        m.put("harga", "32000");
        m.put("id", "2");
        m.put("paket_data", "Paket-OMG-2GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "XL");
        m.put("harga", "39000");
        m.put("id", "3");
        m.put("paket_data", "Paket-OMG-4GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "XL");
        m.put("harga", "63000");
        m.put("id", "4");
        m.put("paket_data", "Paket-OMG-6GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "XL");
        m.put("harga", "89000");
        m.put("id", "5");
        m.put("paket_data", "Paket-OMG-8GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "XL");
        m.put("harga", "25000");
        m.put("id", "6");
        m.put("paket_data", "Freedom-Combo-4GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "XL");
        m.put("harga", "35000");
        m.put("id", "7");
        m.put("paket_data", "Freedom-Combo-6GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "XL");
        m.put("harga", "45000");
        m.put("id", "8");
        m.put("paket_data", "Freedom-Combo-8GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "XL");
        m.put("harga", "65000");
        m.put("id", "9");
        m.put("paket_data", "Freedom-Combo-10GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "XL");
        m.put("harga", "80000");
        m.put("id", "10");
        m.put("paket_data", "Freedom-Combo-12GB");

        // adding map to list
        ja.add(m);

        // putting phoneNumbers to JSONObject
        jo.put("paketData", ja);

        // writing JSON to file:"JSONExample.json" in cwd
        return (jo.toJSONString());
    }

    public String dataPaketSimpati() throws FileNotFoundException {
        // creating JSONObject
        JSONObject jo = new JSONObject();

        // for address data, first create LinkedHashMap
        Map m = new LinkedHashMap(4);

        // for phone numbers, first create JSONArray
        JSONArray ja = new JSONArray();

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Simpati");
        m.put("harga", "27000");
        m.put("id", "1");
        m.put("paket_data", "Paket-Internet-1GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Simpati");
        m.put("harga", "32000");
        m.put("id", "2");
        m.put("paket_data", "Paket-OMG-2GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Simpati");
        m.put("harga", "39000");
        m.put("id", "3");
        m.put("paket_data", "Paket-OMG-4GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Simpati");
        m.put("harga", "63000");
        m.put("id", "4");
        m.put("paket_data", "Paket-OMG-6GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Simpati");
        m.put("harga", "89000");
        m.put("id", "5");
        m.put("paket_data", "Paket-OMG-8GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Simpati");
        m.put("harga", "25000");
        m.put("id", "6");
        m.put("paket_data", "Freedom-Combo-4GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Simpati");
        m.put("harga", "35000");
        m.put("id", "7");
        m.put("paket_data", "Freedom-Combo-6GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Simpati");
        m.put("harga", "45000");
        m.put("id", "8");
        m.put("paket_data", "Freedom-Combo-8GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Simpati");
        m.put("harga", "65000");
        m.put("id", "9");
        m.put("paket_data", "Freedom-Combo-10GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Simpati");
        m.put("harga", "80000");
        m.put("id", "10");
        m.put("paket_data", "Freedom-Combo-12GB");

        // adding map to list
        ja.add(m);

        // putting phoneNumbers to JSONObject
        jo.put("paketData", ja);

        // writing JSON to file:"JSONExample.json" in cwd
        return (jo.toJSONString());
    }

    public String dataPaketAs() throws FileNotFoundException {
        // creating JSONObject
        JSONObject jo = new JSONObject();

        // for address data, first create LinkedHashMap
        Map m = new LinkedHashMap(4);

        // for phone numbers, first create JSONArray
        JSONArray ja = new JSONArray();

        m = new LinkedHashMap(4);
        m.put("nama_provider", "AS");
        m.put("harga", "27000");
        m.put("id", "1");
        m.put("paket_data", "1GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "AS");
        m.put("harga", "32000");
        m.put("id", "2");
        m.put("paket_data", "2GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "AS");
        m.put("harga", "39000");
        m.put("id", "3");
        m.put("paket_data", "4GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "AS");
        m.put("harga", "63000");
        m.put("id", "4");
        m.put("paket_data", "6GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "AS");
        m.put("harga", "89000");
        m.put("id", "5");
        m.put("paket_data", "8GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "AS");
        m.put("harga", "25000");
        m.put("id", "6");
        m.put("paket_data", "4GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "AS");
        m.put("harga", "35000");
        m.put("id", "7");
        m.put("paket_data", "6GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "AS");
        m.put("harga", "45000");
        m.put("id", "8");
        m.put("paket_data", "8GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "AS");
        m.put("harga", "65000");
        m.put("id", "9");
        m.put("paket_data", "10GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "AS");
        m.put("harga", "80000");
        m.put("id", "10");
        m.put("paket_data", "12GB");

        // adding map to list
        ja.add(m);

        // putting phoneNumbers to JSONObject
        jo.put("paketData", ja);

        // writing JSON to file:"JSONExample.json" in cwd
        return (jo.toJSONString());
    }

    public String dataPaketMentari() throws FileNotFoundException {
        // creating JSONObject
        JSONObject jo = new JSONObject();

        // for address data, first create LinkedHashMap
        Map m = new LinkedHashMap(4);

        // for phone numbers, first create JSONArray
        JSONArray ja = new JSONArray();

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Mentari");
        m.put("harga", "27000");
        m.put("id", "1");
        m.put("paket_data", "Paket-Internet-1GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Mentari");
        m.put("harga", "32000");
        m.put("id", "2");
        m.put("paket_data", "Paket-OMG-2GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Mentari");
        m.put("harga", "39000");
        m.put("id", "3");
        m.put("paket_data", "Paket-OMG-4GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Mentari");
        m.put("harga", "63000");
        m.put("id", "4");
        m.put("paket_data", "Paket-OMG-6GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Mentari");
        m.put("harga", "89000");
        m.put("id", "5");
        m.put("paket_data", "Paket-OMG-8GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Mentari");
        m.put("harga", "25000");
        m.put("id", "6");
        m.put("paket_data", "Freedom-Combo-4GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Mentari");
        m.put("harga", "35000");
        m.put("id", "7");
        m.put("paket_data", "Freedom-Combo-6GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Mentari");
        m.put("harga", "45000");
        m.put("id", "8");
        m.put("paket_data", "Freedom-Combo-8GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Mentari");
        m.put("harga", "65000");
        m.put("id", "9");
        m.put("paket_data", "Freedom-Combo-10GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Mentari");
        m.put("harga", "80000");
        m.put("id", "10");
        m.put("paket_data", "Freedom-Combo-12GB");

        // adding map to list
        ja.add(m);

        // putting phoneNumbers to JSONObject
        jo.put("paketData", ja);

        // writing JSON to file:"JSONExample.json" in cwd
        return (jo.toJSONString());
    }

    public String dataPaketAxis() throws FileNotFoundException {
        // creating JSONObject
        JSONObject jo = new JSONObject();

        // for address data, first create LinkedHashMap
        Map m = new LinkedHashMap(4);

        // for phone numbers, first create JSONArray
        JSONArray ja = new JSONArray();

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Axis");
        m.put("harga", "27000");
        m.put("id", "1");
        m.put("paket_data", "Paket-Internet-1GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Axis");
        m.put("harga", "32000");
        m.put("id", "2");
        m.put("paket_data", "Paket-OMG-2GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Axis");
        m.put("harga", "39000");
        m.put("id", "3");
        m.put("paket_data", "Paket-OMG-4GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Axis");
        m.put("harga", "63000");
        m.put("id", "4");
        m.put("paket_data", "Paket-OMG-6GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Axis");
        m.put("harga", "89000");
        m.put("id", "5");
        m.put("paket_data", "Paket-OMG-8GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Axis");
        m.put("harga", "25000");
        m.put("id", "6");
        m.put("paket_data", "Freedom-Combo-4GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Axis");
        m.put("harga", "35000");
        m.put("id", "7");
        m.put("paket_data", "Freedom-Combo-6GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Axis");
        m.put("harga", "45000");
        m.put("id", "8");
        m.put("paket_data", "Freedom-Combo-8GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Axis");
        m.put("harga", "65000");
        m.put("id", "9");
        m.put("paket_data", "Freedom-Combo-10GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Axis");
        m.put("harga", "80000");
        m.put("id", "10");
        m.put("paket_data", "Freedom-Combo-12GB");

        // adding map to list
        ja.add(m);

        // putting phoneNumbers to JSONObject
        jo.put("paketData", ja);

        // writing JSON to file:"JSONExample.json" in cwd
        return (jo.toJSONString());
    }

    public String dataPaketSmartFren() throws FileNotFoundException {
        // creating JSONObject
        JSONObject jo = new JSONObject();

        // for address data, first create LinkedHashMap
        Map m = new LinkedHashMap(4);

        // for phone numbers, first create JSONArray
        JSONArray ja = new JSONArray();

        m = new LinkedHashMap(4);
        m.put("nama_provider", "SmartFren");
        m.put("harga", "27000");
        m.put("id", "1");
        m.put("paket_data", "Paket-Internet-1GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "SmartFren");
        m.put("harga", "32000");
        m.put("id", "2");
        m.put("paket_data", "Paket-OMG-2GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "SmartFren");
        m.put("harga", "39000");
        m.put("id", "3");
        m.put("paket_data", "Paket-OMG-4GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "SmartFren");
        m.put("harga", "63000");
        m.put("id", "4");
        m.put("paket_data", "Paket-OMG-6GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "SmartFren");
        m.put("harga", "89000");
        m.put("id", "5");
        m.put("paket_data", "Paket-OMG-8GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "SmartFren");
        m.put("harga", "25000");
        m.put("id", "6");
        m.put("paket_data", "Freedom-Combo-4GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "SmartFren");
        m.put("harga", "35000");
        m.put("id", "7");
        m.put("paket_data", "Freedom-Combo-6GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "SmartFren");
        m.put("harga", "45000");
        m.put("id", "8");
        m.put("paket_data", "Freedom-Combo-8GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "SmartFren");
        m.put("harga", "65000");
        m.put("id", "9");
        m.put("paket_data", "Freedom-Combo-10GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "SmartFren");
        m.put("harga", "80000");
        m.put("id", "10");
        m.put("paket_data", "Freedom-Combo-12GB");

        // adding map to list
        ja.add(m);

        // putting phoneNumbers to JSONObject
        jo.put("paketData", ja);

        // writing JSON to file:"JSONExample.json" in cwd
        return (jo.toJSONString());
    }

    public String dataPaketThree() throws FileNotFoundException {
        // creating JSONObject
        JSONObject jo = new JSONObject();

        // for address data, first create LinkedHashMap
        Map m = new LinkedHashMap(4);

        // for phone numbers, first create JSONArray
        JSONArray ja = new JSONArray();

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Three");
        m.put("harga", "27000");
        m.put("id", "1");
        m.put("paket_data", "Paket-Internet-1GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Three");
        m.put("harga", "32000");
        m.put("id", "2");
        m.put("paket_data", "Paket-OMG-2GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Three");
        m.put("harga", "39000");
        m.put("id", "3");
        m.put("paket_data", "Paket-OMG-4GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Three");
        m.put("harga", "63000");
        m.put("id", "4");
        m.put("paket_data", "Paket-OMG-6GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Three");
        m.put("harga", "89000");
        m.put("id", "5");
        m.put("paket_data", "Paket-OMG-8GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Three");
        m.put("harga", "25000");
        m.put("id", "6");
        m.put("paket_data", "Freedom-Combo-4GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Three");
        m.put("harga", "35000");
        m.put("id", "7");
        m.put("paket_data", "Freedom-Combo-6GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Three");
        m.put("harga", "45000");
        m.put("id", "8");
        m.put("paket_data", "Freedom-Combo-8GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Three");
        m.put("harga", "65000");
        m.put("id", "9");
        m.put("paket_data", "Freedom-Combo-10GB");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(4);
        m.put("nama_provider", "Three");
        m.put("harga", "80000");
        m.put("id", "10");
        m.put("paket_data", "Freedom-Combo-12GB");

        // adding map to list
        ja.add(m);

        // putting phoneNumbers to JSONObject
        jo.put("paketData", ja);

        // writing JSON to file:"JSONExample.json" in cwd
        return (jo.toJSONString());
    }
}