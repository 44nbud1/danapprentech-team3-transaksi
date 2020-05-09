package project.akhir.danapprentechteam3.readdata;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

@Service
public class DataProviderIndosat {

    public List<String> dataConsumenInosat()
    {
        JSONParser parser = new JSONParser();
        ArrayList<String> data = new ArrayList<>();

        try (Reader reader = new FileReader("/Users/setiawanaa/IdeaProjects/danapprentech-team3/src/main/resources/indosat/customerIndosat.json")) {

            Object object = new JSONParser().parse(reader);

            JSONObject jsonObject = (JSONObject) object;

            Map indosatProvider = new HashMap();
            Iterator<Map.Entry> itr1 = indosatProvider.entrySet().iterator();

            JSONArray ja = (JSONArray) jsonObject.get("providerIndosat");

            // iterating provider indosat
            Iterator itr2 = ja.iterator();
            while (itr2.hasNext())
            {
                itr1 = ((Map) itr2.next()).entrySet().iterator();
                while (itr1.hasNext()) {
                    Map.Entry pair = itr1.next();
                    data.add((String) pair.getValue());
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}