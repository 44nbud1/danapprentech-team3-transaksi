package project.akhir.danapprentechteam3.login.readdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.akhir.danapprentechteam3.login.readdata.model.DataProviderIndosat;
import project.akhir.danapprentechteam3.login.readdata.model.DataProviderXl;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProviderValidation
{
    @Autowired
    DataProviderIndosat dataProviderIndosat;

    @Autowired
    DataProviderXl dataProviderXl;

    public boolean validasiProvider (String noTelp)
    {
        List<String> allProvider = new ArrayList<>();
        boolean status = false;
        List<String> xl = dataProviderXl.dataConsumenXl();
        List<String> indosat = dataProviderIndosat.dataConsumenIndosat();
        indosat.addAll(xl);

        for (int i = 0; i < indosat.size(); i++)
        {
            String data = indosat.get(i);
            if (data.equalsIgnoreCase(noTelp))
            {
                status = true;
            }
        }
        return status;
    }
}
