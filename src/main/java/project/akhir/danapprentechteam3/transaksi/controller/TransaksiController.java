package project.akhir.danapprentechteam3.transaksi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.akhir.danapprentechteam3.login.models.User;
import project.akhir.danapprentechteam3.login.repository.UserRepository;
import project.akhir.danapprentechteam3.transaksi.model.Transaksi;
import project.akhir.danapprentechteam3.transaksi.rabbitMQ.consumer.TransaksiMessageListener;
import project.akhir.danapprentechteam3.transaksi.rabbitMQ.producer.TransaksiMessageSender;
import project.akhir.danapprentechteam3.transaksi.service.ServiceTransaksi;
import project.akhir.danapprentechteam3.transaksi.service.ServiceTransaksiImpl;

import java.util.Date;

@RestController
@RequestMapping("/api/transaksi")
public class TransaksiController {

    UserRepository userRepository;

    @Autowired
    TransaksiMessageSender trxSender;

    @Autowired
    ServiceTransaksiImpl serviceTransaksi;

    @Autowired
    TransaksiMessageListener trxListener;

    @PostMapping("/E-wallet")
    public ResponseEntity<?> transaksiEwallet(@RequestBody User user){

        User dataUser = userRepository.findByNoTelepon(user.getNoTelepon());

        Transaksi transaksi = serviceTransaksi.findByNoTelepon(user.getNoTelepon());

        transaksi.setNamaUser(dataUser.getNamaUser());
        transaksi.setTanggal(new Date());
        transaksi.setHarga(27000L); // seharusnya get
        transaksi.setNamaProvider("Mentari"); // seharusnya get
        transaksi.setPaketData("Paket-Internet-1GB"); // seharusnya get
        transaksi.setNomorPaketData("085777488828");  // harusnya get
        transaksi.setStatusPembayaran(true);
        transaksi.setPembayaranMelalui("E-Wallet");

        if (transaksi != null)
        {
            return ResponseEntity.badRequest().body("Pesanan mu sudah dibayar...!");
        }

        if (dataUser.getSaldo() < transaksi.getHarga())
        {
            return ResponseEntity.badRequest().body("Saldomu kurang silakan isi ulang saldo mu sekarang");
        }
        dataUser.setSaldo(dataUser.getSaldo()- transaksi.getHarga());
        userRepository.save(dataUser);
        return new ResponseEntity<>(serviceTransaksi.saveTransaksi(transaksi),HttpStatus.BAD_REQUEST);
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
