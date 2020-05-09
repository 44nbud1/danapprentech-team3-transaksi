package project.akhir.danapprentechteam3.readdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.akhir.danapprentechteam3.readdata.model.DataProviderIndosat;
import project.akhir.danapprentechteam3.readdata.model.DataProviderXl;

import java.util.List;

@Service
public class ProviderValidation
{
    @Autowired
    DataProviderIndosat dataProviderIndosat;

    @Autowired
    DataProviderXl dataProviderXl;

    public boolean validProviderXl (String username)
    {
        boolean status = false;
        List<String> xl = dataProviderXl.dataConsumenXl();
        for (int i = 0; i < xl.size(); i++)
        {
            String dataXl = xl.get(i);
            if (dataXl == username)
            {
                status = true;
            }
            break;
        }
        return status;
    }

    public boolean validProviderIndosat(String username)
    {
        boolean status = false;
        List<String> indosat = dataProviderIndosat.dataConsumenIndosat();
        for (int i = 0; i < indosat.size(); i++)
        {
            String dataIndosat = indosat.get(i);

            if (dataIndosat == username)
            {
                status = true;
            }
            break;
        }
        return status;
    }
}
