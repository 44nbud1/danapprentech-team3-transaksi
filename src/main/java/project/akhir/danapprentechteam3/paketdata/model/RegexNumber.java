package project.akhir.danapprentechteam3.paketdata.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.akhir.danapprentechteam3.paketdata.service.PaketData;

import java.io.FileNotFoundException;
import java.util.regex.Pattern;


@Service
public class RegexNumber {

    @Autowired
    private PaketData paketData;

    public String validatePhoneNumber(String phoneNo) throws FileNotFoundException {
        String simpatiregex = "^(\\+62|\\+0|0|62)8(12|13|21|22|23)[0-9]{5,9}$";
        String asregex = "^(\\+62|\\+0|0|62)8(52|53|23|51)[0-9]{5,9}$";
        String mentariregex= "^(\\+62|\\+0|0|62)8(15|16|58)[0-9]{5,9}$";
        String im3regex = "^(\\+62|\\+0|0|62)8(14|55|57)[0-9]{5,9}$";
        String xlregex = "^(\\+62|\\+0|0|62)8(17|18|19|59|76|77|78)[0-9]{5,9}$";
        String three = "^(\\+62|\\+0|0|62)8(95|96|97|98|99)[0-9]{5,9}$";
        String smartfren = "^(\\+62|\\+0|0|62)8(81|82|83|84|85|86|87|88|89)[0-9]{5,9}$";
        String axis = "^(\\+62|\\+0|0|62)8(38|31|32|33)[0-9]{5,9}$";


        if(Pattern.matches(simpatiregex,phoneNo)){
            return paketData.dataPaketSimpati();
        }else if(Pattern.matches(asregex,phoneNo)){
            return paketData.dataPaketAs();
        }else if(Pattern.matches(mentariregex,phoneNo)){
            return paketData.dataPaketMentari();
        }else if(Pattern.matches(im3regex,phoneNo)){
            return paketData.dataPaketIm3();
        }else if(Pattern.matches(xlregex,phoneNo)){
            return paketData.dataPaketXl();
        }else if(Pattern.matches(three,phoneNo)){
            return paketData.dataPaketThree();
        }else if(Pattern.matches(smartfren,phoneNo)){
            return paketData.dataPaketSmartFren();
        }else if(Pattern.matches(axis,phoneNo)){
            return paketData.dataPaketAxis();
        }else {
            return "Invalid Number";
        }
    }
}
