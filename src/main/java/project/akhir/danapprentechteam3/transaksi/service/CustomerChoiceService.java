package project.akhir.danapprentechteam3.transaksi.service;

import org.springframework.stereotype.Service;
import project.akhir.danapprentechteam3.transaksi.model.CustomerChoice;

@Service
public class CustomerChoiceService
{

    public static CustomerChoice pilihanPaketData(CustomerChoice customerChoice)
    {
        CustomerChoice choice = new CustomerChoice();

        switch (customerChoice.getNamaProvider())
        {
            case "indosat" :
                switch (customerChoice.getId()){
                    case 1 :
                        choice.setHarga(27000L);
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Paket-OMG-1GB");
                        break;
                    case 2 :
                        choice.setHarga(32000L);
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Paket-OMG-2GB");
                        break;
                    case 3 :
                        choice.setHarga(39000L);
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Paket-OMG-3GB");
                        break;
                    case 4 :
                        choice.setHarga(63000L );
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Paket-OMG-4GB");
                        break;
                    case 5 :
                        choice.setHarga(89000L);
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Paket-OMG-6GB");
                        break;
                    case 6 :
                        choice.setHarga(25000L);
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Freedom-Combo-4GB");
                        break;
                    case 7 :
                        choice.setHarga(35000L);
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Freedom-Combo-6GB");
                        break;
                    case 8 :
                        choice.setHarga(45000L);
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Freedom-Combo-8GB");
                        break;
                    case 9 :
                        choice.setHarga(65000L);
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Freedom-Combo-10GB");
                        break;
                    case 10 :
                        choice.setHarga(80000L);
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Freedom-Combo-12GB");
                        break;
                }

            case "simpati" :
                switch (customerChoice.getId()){
                    case 1 :
                        choice.setHarga(27000L);
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Paket-OMG-1GB");
                        break;
                    case 2 :
                        choice.setHarga(32000L);
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Paket-OMG-2GB");
                        break;
                    case 3 :
                        choice.setHarga(39000L);
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Paket-OMG-3GB");
                        break;
                    case 4 :
                        choice.setHarga(63000L );
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Paket-OMG-4GB");
                        break;
                    case 5 :
                        choice.setHarga(89000L);
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Paket-OMG-6GB");
                        break;
                    case 6 :
                        choice.setHarga(25000L);
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Freedom-Combo-4GB");
                        break;
                    case 7 :
                        choice.setHarga(35000L);
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Freedom-Combo-6GB");
                        break;
                    case 8 :
                        choice.setHarga(45000L);
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Freedom-Combo-8GB");
                        break;
                    case 9 :
                        choice.setHarga(65000L);
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Freedom-Combo-10GB");
                        break;
                    case 10 :
                        choice.setHarga(80000L);
                        choice.setNamaProvider("indosat");
                        choice.setPaketData("Freedom-Combo-12GB");
                        break;
                }
        }
        return choice;
    }
}
