package project.akhir.danapprentechteam3.transaksi.service;

import org.springframework.stereotype.Service;
import project.akhir.danapprentechteam3.transaksi.model.CustomerChoice;


@Service
public class CustomerChoiceService

{
//    public static CustomerChoice pilihanPaketData(CustomerChoice customerChoice)
//    {
//        CustomerChoice choice = new CustomerChoice();
//
//        switch (customerChoice.getNamaProvider())
//        {
//            case "indosat" :
//                switch (customerChoice.getId()){
//                    case 1 :
//                        choice.setHarga(27000L);
//                        choice.setNamaProvider("indosat");
//                        choice.setPaketData("Paket-OMG-1GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 2 :
//                        choice.setHarga(32000L);
//                        choice.setNamaProvider("indosat");
//                        choice.setPaketData("Paket-OMG-2GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 3 :
//                        choice.setHarga(39000L);
//                        choice.setNamaProvider("indosat");
//                        choice.setPaketData("Paket-OMG-3GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 4 :
//                        choice.setHarga(63000L );
//                        choice.setNamaProvider("indosat");
//                        choice.setPaketData("Paket-OMG-4GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 5 :
//                        choice.setHarga(89000L);
//                        choice.setNamaProvider("indosat");
//                        choice.setPaketData("Paket-OMG-6GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 6 :
//                        choice.setHarga(25000L);
//                        choice.setNamaProvider("indosat");
//                        choice.setPaketData("Freedom-Combo-4GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 7 :
//                        choice.setHarga(35000L);
//                        choice.setNamaProvider("indosat");
//                        choice.setPaketData("Freedom-Combo-6GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 8 :
//                        choice.setHarga(45000L);
//                        choice.setNamaProvider("indosat");
//                        choice.setPaketData("Freedom-Combo-8GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 9 :
//                        choice.setHarga(65000L);
//                        choice.setNamaProvider("indosat");
//                        choice.setPaketData("Freedom-Combo-10GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 10 :
//                        choice.setHarga(80000L);
//                        choice.setNamaProvider("indosat");
//                        choice.setPaketData("Freedom-Combo-12GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                }
//
//            case "simpati" :
//                switch (customerChoice.getId()){
//                    case 1 :
//                        choice.setHarga(27000L);
//                        choice.setNamaProvider("simpati");
//                        choice.setPaketData("Paket-OMG-1GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 2 :
//                        choice.setHarga(32000L);
//                        choice.setNamaProvider("simpati");
//                        choice.setPaketData("Paket-OMG-2GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 3 :
//                        choice.setHarga(39000L);
//                        choice.setNamaProvider("simpati");
//                        choice.setPaketData("Paket-OMG-3GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 4 :
//                        choice.setHarga(63000L );
//                        choice.setNamaProvider("simpati");
//                        choice.setPaketData("Paket-OMG-4GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 5 :
//                        choice.setHarga(89000L);
//                        choice.setNamaProvider("simpati");
//                        choice.setPaketData("Paket-OMG-6GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 6 :
//                        choice.setHarga(25000L);
//                        choice.setNamaProvider("simpati");
//                        choice.setPaketData("Freedom-Combo-4GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 7 :
//                        choice.setHarga(35000L);
//                        choice.setNamaProvider("simpati");
//                        choice.setPaketData("Freedom-Combo-6GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 8 :
//                        choice.setHarga(45000L);
//                        choice.setNamaProvider("simpati");
//                        choice.setPaketData("Freedom-Combo-8GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 9 :
//                        choice.setHarga(65000L);
//                        choice.setNamaProvider("simpati");
//                        choice.setPaketData("Freedom-Combo-10GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 10 :
//                        choice.setHarga(80000L);
//                        choice.setNamaProvider("simpati");
//                        choice.setPaketData("Freedom-Combo-12GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                }
//
//            case "xl" :
//                switch (customerChoice.getId()){
//                    case 1 :
//                        choice.setHarga(27000L);
//                        choice.setNamaProvider("xl");
//                        choice.setPaketData("Paket-OMG-1GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 2 :
//                        choice.setHarga(32000L);
//                        choice.setNamaProvider("xl");
//                        choice.setPaketData("Paket-OMG-2GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 3 :
//                        choice.setHarga(39000L);
//                        choice.setNamaProvider("xl");
//                        choice.setPaketData("Paket-OMG-3GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 4 :
//                        choice.setHarga(63000L );
//                        choice.setNamaProvider("xl");
//                        choice.setPaketData("Paket-OMG-4GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 5 :
//                        choice.setHarga(89000L);
//                        choice.setNamaProvider("xl");
//                        choice.setPaketData("Paket-OMG-6GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 6 :
//                        choice.setHarga(25000L);
//                        choice.setNamaProvider("xl");
//                        choice.setPaketData("Freedom-Combo-4GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 7 :
//                        choice.setHarga(35000L);
//                        choice.setNamaProvider("xl");
//                        choice.setPaketData("Freedom-Combo-6GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 8 :
//                        choice.setHarga(45000L);
//                        choice.setNamaProvider("xl");
//                        choice.setPaketData("Freedom-Combo-8GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 9 :
//                        choice.setHarga(65000L);
//                        choice.setNamaProvider("xl");
//                        choice.setPaketData("Freedom-Combo-10GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 10 :
//                        choice.setHarga(80000L);
//                        choice.setNamaProvider("xl");
//                        choice.setPaketData("Freedom-Combo-12GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                }
//
//            case "as" :
//                switch (customerChoice.getId()){
//                    case 1 :
//                        choice.setHarga(27000L);
//                        choice.setNamaProvider("as");
//                        choice.setPaketData("Paket-OMG-1GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 2 :
//                        choice.setHarga(32000L);
//                        choice.setNamaProvider("as");
//                        choice.setPaketData("Paket-OMG-2GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 3 :
//                        choice.setHarga(39000L);
//                        choice.setNamaProvider("as");
//                        choice.setPaketData("Paket-OMG-3GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 4 :
//                        choice.setHarga(63000L );
//                        choice.setNamaProvider("as");
//                        choice.setPaketData("Paket-OMG-4GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 5 :
//                        choice.setHarga(89000L);
//                        choice.setNamaProvider("as");
//                        choice.setPaketData("Paket-OMG-6GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 6 :
//                        choice.setHarga(25000L);
//                        choice.setNamaProvider("as");
//                        choice.setPaketData("Freedom-Combo-4GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 7 :
//                        choice.setHarga(35000L);
//                        choice.setNamaProvider("as");
//                        choice.setPaketData("Freedom-Combo-6GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 8 :
//                        choice.setHarga(45000L);
//                        choice.setNamaProvider("as");
//                        choice.setPaketData("Freedom-Combo-8GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 9 :
//                        choice.setHarga(65000L);
//                        choice.setNamaProvider("as");
//                        choice.setPaketData("Freedom-Combo-10GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 10 :
//                        choice.setHarga(80000L);
//                        choice.setNamaProvider("as");
//                        choice.setPaketData("Freedom-Combo-12GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                }
//
//            case "three" :
//                switch (customerChoice.getId()){
//                    case 1 :
//                        choice.setHarga(27000L);
//                        choice.setNamaProvider("three");
//                        choice.setPaketData("Paket-OMG-1GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 2 :
//                        choice.setHarga(32000L);
//                        choice.setNamaProvider("three");
//                        choice.setPaketData("Paket-OMG-2GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 3 :
//                        choice.setHarga(39000L);
//                        choice.setNamaProvider("three");
//                        choice.setPaketData("Paket-OMG-3GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 4 :
//                        choice.setHarga(63000L );
//                        choice.setNamaProvider("three");
//                        choice.setPaketData("Paket-OMG-4GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 5 :
//                        choice.setHarga(89000L);
//                        choice.setNamaProvider("three");
//                        choice.setPaketData("Paket-OMG-6GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 6 :
//                        choice.setHarga(25000L);
//                        choice.setNamaProvider("three");
//                        choice.setPaketData("Freedom-Combo-4GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 7 :
//                        choice.setHarga(35000L);
//                        choice.setNamaProvider("three");
//                        choice.setPaketData("Freedom-Combo-6GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 8 :
//                        choice.setHarga(45000L);
//                        choice.setNamaProvider("three");
//                        choice.setPaketData("Freedom-Combo-8GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 9 :
//                        choice.setHarga(65000L);
//                        choice.setNamaProvider("three");
//                        choice.setPaketData("Freedom-Combo-10GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 10 :
//                        choice.setHarga(80000L);
//                        choice.setNamaProvider("three");
//                        choice.setPaketData("Freedom-Combo-12GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                }
//
//            case "smartfren" :
//                switch (customerChoice.getId()){
//                    case 1 :
//                        choice.setHarga(27000L);
//                        choice.setNamaProvider("smartfren");
//                        choice.setPaketData("Paket-OMG-1GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 2 :
//                        choice.setHarga(32000L);
//                        choice.setNamaProvider("smartfren");
//                        choice.setPaketData("Paket-OMG-2GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 3 :
//                        choice.setHarga(39000L);
//                        choice.setNamaProvider("smartfren");
//                        choice.setPaketData("Paket-OMG-3GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 4 :
//                        choice.setHarga(63000L );
//                        choice.setNamaProvider("smartfren");
//                        choice.setPaketData("Paket-OMG-4GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 5 :
//                        choice.setHarga(89000L);
//                        choice.setNamaProvider("smartfren");
//                        choice.setPaketData("Paket-OMG-6GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 6 :
//                        choice.setHarga(25000L);
//                        choice.setNamaProvider("smartfren");
//                        choice.setPaketData("Freedom-Combo-4GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 7 :
//                        choice.setHarga(35000L);
//                        choice.setNamaProvider("smartfren");
//                        choice.setPaketData("Freedom-Combo-6GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 8 :
//                        choice.setHarga(45000L);
//                        choice.setNamaProvider("smartfren");
//                        choice.setPaketData("Freedom-Combo-8GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 9 :
//                        choice.setHarga(65000L);
//                        choice.setNamaProvider("smartfren");
//                        choice.setPaketData("Freedom-Combo-10GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 10 :
//                        choice.setHarga(80000L);
//                        choice.setNamaProvider("smartfren");
//                        choice.setPaketData("Freedom-Combo-12GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                }
//
//            case "axis" :
//                switch (customerChoice.getId()){
//                    case 1 :
//                        choice.setHarga(27000L);
//                        choice.setNamaProvider("axis");
//                        choice.setPaketData("Paket-OMG-1GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 2 :
//                        choice.setHarga(32000L);
//                        choice.setNamaProvider("axis");
//                        choice.setPaketData("Paket-OMG-2GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 3 :
//                        choice.setHarga(39000L);
//                        choice.setNamaProvider("axis");
//                        choice.setPaketData("Paket-OMG-3GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 4 :
//                        choice.setHarga(63000L );
//                        choice.setNamaProvider("axis");
//                        choice.setPaketData("Paket-OMG-4GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 5 :
//                        choice.setHarga(89000L);
//                        choice.setNamaProvider("axis");
//                        choice.setPaketData("Paket-OMG-6GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 6 :
//                        choice.setHarga(25000L);
//                        choice.setNamaProvider("axis");
//                        choice.setPaketData("Freedom-Combo-4GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 7 :
//                        choice.setHarga(35000L);
//                        choice.setNamaProvider("axis");
//                        choice.setPaketData("Freedom-Combo-6GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 8 :
//                        choice.setHarga(45000L);
//                        choice.setNamaProvider("axis");
//                        choice.setPaketData("Freedom-Combo-8GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 9 :
//                        choice.setHarga(65000L);
//                        choice.setNamaProvider("axis");
//                        choice.setPaketData("Freedom-Combo-10GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                    case 10 :
//                        choice.setHarga(80000L);
//                        choice.setNamaProvider("axis");
//                        choice.setPaketData("Freedom-Combo-12GB");
//                        customerChoice.getNomorPaketData();
//                        break;
//                }
//        }
//        return choice;
//    }
}
