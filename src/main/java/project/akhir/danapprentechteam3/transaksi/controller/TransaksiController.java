package project.akhir.danapprentechteam3.transaksi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.akhir.danapprentechteam3.login.models.User;
import project.akhir.danapprentechteam3.login.repository.UserRepository;
import project.akhir.danapprentechteam3.transaksi.model.Transaksi;
import project.akhir.danapprentechteam3.transaksi.model.transaksirequest.TransaksiRequest;
import project.akhir.danapprentechteam3.transaksi.service.ServiceTransaksiImpl;

import java.util.Date;

@RestController
@RequestMapping("/api/transaksi")
public class TransaksiController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceTransaksiImpl serviceTransaksi;

    @PostMapping("/E-wallet")
    public ResponseEntity<?> transaksiEwallet(@RequestBody TransaksiRequest request){

        User dataUser = userRepository.findByNoTelepon(request.getNoTelepon());

        System.out.println(dataUser);

        Transaksi transaksi = new Transaksi();
        transaksi.setPembayaranMelalui("E-Walet");
//        transaksi.setNomorPaketData();
//        transaksi.setNamaUser(dataUser.getNamaUser());


//        serviceTransaksi.saveTransaksi(transaksi);

//        if (operationTransaksi.isStatusPembayaran())
//        {
//            return ResponseEntity.badRequest().body("Pesanan mu sudah dibayar...!");
//        }

        if (dataUser.getSaldo() < transaksi.getHarga())
        {
            return ResponseEntity.badRequest().body("Saldomu kurang silakan isi ulang saldo mu sekarang");
        }
        dataUser.setSaldo(dataUser.getSaldo()- transaksi.getHarga());
        userRepository.save(dataUser);

        return new ResponseEntity<>("sedewdseded",HttpStatus.BAD_REQUEST);
    }

//    @PostMapping("/trx/va/")
//    public ResponseEntity<?> transaksiVA(@RequestBody Virtual va){
//        Transaksi transaksi = trxListener.getTransaksi();
////        String virtualAccount = transaksiMapper.virtualAccount(transaksi.getUsername());
//        if(va.getVirtual().equals(virtualAccount)){
////            transaksiMapper.insert(transaksi);
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("msg","pembelian berhasil");
//            return new ResponseEntity<>(jsonObject,HttpStatus.OK);
//        }else {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("msg","virtual account invalid");
//            return new ResponseEntity<>(jsonObject,HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/history")
//    public ResponseEntity<?> getHistory(@RequestBody User user){
//        List<Transaksi> transaksi= transaksiMapper.findByUsername(user.getEmail());
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("history",transaksi);
//        return new ResponseEntity<>(jsonObject,HttpStatus.OK);
//    }

}
