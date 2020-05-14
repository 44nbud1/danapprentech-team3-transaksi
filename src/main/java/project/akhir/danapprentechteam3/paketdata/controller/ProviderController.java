package project.akhir.danapprentechteam3.paketdata.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.akhir.danapprentechteam3.login.payload.response.MessageResponse;
import project.akhir.danapprentechteam3.paketdata.model.Phone;
import project.akhir.danapprentechteam3.paketdata.model.Provider;
import project.akhir.danapprentechteam3.paketdata.model.RegexNumber;
import project.akhir.danapprentechteam3.paketdata.repository.ProviderRepository;
import project.akhir.danapprentechteam3.paketdata.service.PaketData;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/provider")
public class ProviderController {

    @Autowired
    ProviderRepository providerRepository;

    @Autowired
    RegexNumber regexNumber;

    @Autowired
    PaketData paketData;

    @GetMapping("/all")
    public List<Provider> getAll(){
        return providerRepository.findAll();
    }

    @PostMapping("/add")
    public List<Provider> persist(@RequestBody final Provider provider){
        providerRepository.save(provider);
        return providerRepository.findAll();
    }

    @PostMapping("/cek-paket")
    public ResponseEntity<?> testOaket(@RequestBody Phone phone) throws FileNotFoundException {

        if (regexNumber.validatePhoneNumber(phone.getNomer_hp()).equalsIgnoreCase("Invalid Number"))
        {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid number , please check...!","400"));
        }

        return new ResponseEntity<>(regexNumber.validatePhoneNumber(phone.getNomer_hp()),HttpStatus.OK);
    }

}
