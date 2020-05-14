package project.akhir.danapprentechteam3.transaksi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import project.akhir.danapprentechteam3.login.models.User;
import project.akhir.danapprentechteam3.login.payload.response.MessageResponse;
import project.akhir.danapprentechteam3.login.repository.UserRepository;
import project.akhir.danapprentechteam3.transaksi.model.CustomerChoice;
import project.akhir.danapprentechteam3.transaksi.model.Transaksi;
import project.akhir.danapprentechteam3.transaksi.model.transaksirequest.TransaksiRequest;
import project.akhir.danapprentechteam3.transaksi.repository.TransaksiRepository;
import project.akhir.danapprentechteam3.transaksi.service.CustomerChoiceRepository;
import project.akhir.danapprentechteam3.transaksi.service.CustomerChoiceService;
import project.akhir.danapprentechteam3.transaksi.service.ServiceTransaksiImpl;

import java.util.Date;

@Transactional
@RestController
@RequestMapping("/api/transaksi")
public class TransaksiController {

    @Autowired
    CustomerChoiceRepository customerChoiceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceTransaksiImpl serviceTransaksi;

    @Autowired
    TransaksiRepository transaksiRepository;

    @Autowired
    CustomerChoiceService customerChoiceService;

    @PostMapping("/E-wallet")
    public ResponseEntity<?> transaksiEwallet(@RequestBody TransaksiRequest request){

        User dataUser = userRepository.findByNoTelepon(request.getNoTelepon());
        CustomerChoice choice = customerChoiceRepository.findByNoTelepon(request.getNoTelepon());

        if (dataUser == null)
        {
            return ResponseEntity.badRequest().body(new MessageResponse(
                    "Anda Tidak memiliki transaksi", "400"));
        }

        if (dataUser.getPinTransaksi() == (request.getPinTransaksi())) {
            Transaksi transaksi = new Transaksi();
            transaksi.setPembayaranMelalui("E-Walet");
            transaksi.setNomorPaketData(choice.getNomorPaketData());
            transaksi.setNamaUser("bambang");
            transaksi.setHarga(choice.getHarga());
            transaksi.setNamaProvider(choice.getNamaProvider());
            transaksi.setNomorTeleponUser(request.getNoTelepon());
            transaksi.setPaketData(choice.getPaketData());
            transaksi.setNomorPaketData("09774957495");
            transaksi.isStatusPembayaran();
            transaksi.setTanggal(new Date());

            if (dataUser.getSaldo() < transaksi.getHarga()) {
                return ResponseEntity.badRequest().body(new MessageResponse(
                        "Saldomu kurang silakan isi ulang saldo mu sekarang", "400"));
            }

            if (choice.isStatusTransaksi()) {
                return ResponseEntity.badRequest().body(new MessageResponse(
                        "Pesanan mu sudah dibayar", "400"));
            }

            choice.setStatusTransaksi(true);
            customerChoiceRepository.save(choice);
            transaksi.setStatusPembayaran(true);
            transaksiRepository.save(transaksi);
            dataUser.setSaldo(dataUser.getSaldo() - transaksi.getHarga());
            userRepository.save(dataUser);

            return new ResponseEntity<>(transaksi, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse("Wrong Transaction Pin, Please" +
                    " try again","400"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/virtual-account")
    public ResponseEntity<?> transaksiVA(@RequestBody TransaksiRequest request){

    User dataUser = userRepository.findByNoTelepon(request.getNoTelepon());
    CustomerChoice choice = customerChoiceRepository.findByNoTelepon(request.getNoTelepon());

    if (dataUser == null)
        {
            return ResponseEntity.badRequest().body(new MessageResponse(
                    "Anda Tidak memiliki transaksi", "400"));
        }

    if (dataUser.getVirtualAccount().equals(request.getVirtualAccount())) {
        Transaksi transaksi = new Transaksi();
        transaksi.setPembayaranMelalui("M-Banking");
        transaksi.setNomorPaketData(choice.getNomorPaketData());
        transaksi.setNamaUser("bambang");
        transaksi.setHarga(choice.getHarga());
        transaksi.setNamaProvider(choice.getNamaProvider());
        transaksi.setNomorTeleponUser(request.getNoTelepon());
        transaksi.setPaketData(choice.getPaketData());
        transaksi.setNomorPaketData("09774957495");
        transaksi.isStatusPembayaran();
        transaksi.setTanggal(new Date());

        if (dataUser.getSaldo() < transaksi.getHarga()) {
            return ResponseEntity.badRequest().body(new MessageResponse(
                    "Saldomu kurang silakan isi ulang saldo mu sekarang", "400"));
        }

        if (choice.isStatusTransaksi()) {
            return ResponseEntity.badRequest().body(new MessageResponse(
                    "Pesanan mu sudah dibayar", "400"));
        }

        choice.setStatusTransaksi(true);
        customerChoiceRepository.save(choice);
        transaksi.setStatusPembayaran(true);
        Long saldoAkhir = dataUser.getSaldo() - transaksi.getHarga();
        dataUser.setSaldo(dataUser.getSaldo() - transaksi.getHarga());
        transaksi.setSaldoAkhir(saldoAkhir);

        transaksiRepository.save(transaksi);
        userRepository.save(dataUser);

        return new ResponseEntity<>(transaksi, HttpStatus.OK);
    } else {
            return new ResponseEntity<>(new MessageResponse("Wrong Virtual Account, Please" +
                    " try again","400"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/choice")
    public ResponseEntity<?> findPaketData(@RequestBody CustomerChoice customerChoice)
    {
        CustomerChoice choice = new CustomerChoice();
        choice.setStatusTransaksi(false);
        choice.setHarga(customerChoice.getHarga());
        choice.setNoTelepon(customerChoice.getNoTelepon());
        choice.setWaktuTransaksi(new Date());
        choice.setNamaProvider(customerChoice.getNamaProvider());
        choice.setPaketData(customerChoice.getPaketData());
        choice.setNomorPaketData(customerChoice.getNomorPaketData());

        CustomerChoice choices = customerChoiceRepository.findByNoTelepon(customerChoice.getNoTelepon());

        if (choice != null)
        {
            customerChoiceRepository.deleteByNoTelepon(customerChoice.getNoTelepon());
        }

        customerChoiceRepository.save(choice);
        return new ResponseEntity<>(customerChoiceRepository.save(choice),HttpStatus.OK);
    }

    @PostMapping("/history/{mobileNumber}")
    public ResponseEntity<?> hystoryTransaksi(@PathVariable String  mobileNumber)
    {
        Transaksi transaksi = transaksiRepository.findByNomorTeleponUser(mobileNumber);

        if (transaksi == null)
        {
            return ResponseEntity.badRequest().body(new MessageResponse(
                    "Anda tidak memiliki transaksi", "400"));
        }

        return new ResponseEntity<>(transaksi,HttpStatus.OK);
    }
}
