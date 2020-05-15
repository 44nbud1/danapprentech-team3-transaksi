package project.akhir.danapprentechteam3.transaksi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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
import project.akhir.danapprentechteam3.transaksi.uploadfile.model.DBFile;
import project.akhir.danapprentechteam3.transaksi.uploadfile.payload.UploadFileResponse;
import project.akhir.danapprentechteam3.transaksi.uploadfile.service.DBFileStorageService;


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

    @Autowired
    DBFileStorageService dbFileStorageService;

    @PostMapping("/E-wallet")
    public ResponseEntity<?> transaksiEwallet(@RequestBody TransaksiRequest request){

        User dataUser = userRepository.findByNoTelepon(request.getNoTelepon());
        CustomerChoice choice = customerChoiceRepository.findByNoTelepon(request.getNoTelepon());

        if (dataUser == null)
        {
            return ResponseEntity.badRequest().body(new MessageResponse(
                    "Anda Tidak memiliki transaksi", "400"));
        }

        if (choice == null)
        {
            return ResponseEntity.badRequest().body(new MessageResponse(
                    "Anda Tidak memiliki transaksi", "400"));
        }

        System.out.println(dataUser.getPinTransaksi());
        System.out.println(request.getPinTransaksi());

        if (String.valueOf(dataUser.getPinTransaksi()).equalsIgnoreCase(String.valueOf(request.getPinTransaksi())))
        {
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

            transaksi.setSaldoAkhir(dataUser.getSaldo() - transaksi.getHarga());
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

    if ((dataUser.getVirtualAccount().equals(request.getVirtualAccount()))) {
        Transaksi transaksi = new Transaksi();
        transaksi.setPembayaranMelalui("M-Banking");
        transaksi.setNomorPaketData(choice.getNomorPaketData());
        transaksi.setNamaUser("bambang");
        transaksi.setHarga(choice.getHarga());
        transaksi.setNamaProvider(choice.getNamaProvider());
        transaksi.setNomorTeleponUser(request.getNoTelepon());
        transaksi.setPaketData(choice.getPaketData());
        transaksi.setNomorPaketData(choice.getNomorPaketData());
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
//
//        if (transaksi.isStatusUpload() == false) {
//            return ResponseEntity.badRequest().body(new MessageResponse(
//                    "Please , Upload Bukti pembayaran", "400"));
//        }

        choice.setStatusTransaksi(true);
        customerChoiceRepository.save(choice);
        transaksi.setStatusPembayaran(true);
        Long saldoAkhir = dataUser.getSaldo() - transaksi.getHarga();
        dataUser.setSaldo(dataUser.getSaldo() - transaksi.getHarga());
        transaksi.setSaldoAkhir(saldoAkhir);

        transaksiRepository.save(transaksi);
        userRepository.save(dataUser);

        return ResponseEntity.ok(new MessageResponse("Transaction successfully, please upload photo transaction","200"));
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


//    @PostMapping("/virtual-account")
//    public ResponseEntity<?> transaksiVirtualAccount(@RequestBody TransaksiRequest request)
//    {
//
//        User dataUser = userRepository.findByNoTelepon(request.getNoTelepon());
//        CustomerChoice choice = customerChoiceRepository.findByNoTelepon(request.getNoTelepon());
//        if (String.valueOf(dataUser.getPinTransaksi()).equalsIgnoreCase(String.valueOf(request.getPinTransaksi())))
//        {
//            if (dataUser == null)
//            {
//                return ResponseEntity.badRequest().body(new MessageResponse(
//                        "Anda Tidak memiliki transaksi", "400"));
//            }
//
//
//            if (dataUser.getSaldo() < choice.getHarga())
//            {
//                return ResponseEntity.badRequest().body(new MessageResponse(
//                        "Saldomu kurang silakan isi ulang saldo mu sekarang", "400"));
//            }
//
//            if (choice.isStatusTransaksi())
//            {
//                return ResponseEntity.badRequest().body(new MessageResponse(
//                        "Pesanan mu sudah dibayar", "400"));
//            }
//
//            return ResponseEntity.ok(new MessageResponse("Transaction successfully, please upload photo transaction","200"));
//        } else {
//            return new ResponseEntity<>(new MessageResponse("Wrong Virtual Account, Please" +
//                    " try again","400"), HttpStatus.BAD_REQUEST);
//        }
//
//    }

//    @PostMapping("/buktipembayaran/")
    @RequestMapping(value = "/buktipembayaran" , method = RequestMethod.POST, consumes = { "multipart/form-data" })
    private ResponseEntity<?> uploadFileResponse(@RequestParam("file") MultipartFile file)
    {

        DBFile dbFile = dbFileStorageService.storeFile(file);


        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/transaksi-upload-photo/downloadFile/")
                .path(String.valueOf(dbFile.getId()))
                .toUriString();
        if (fileDownloadUri == null)
        {
            return new ResponseEntity<>(new MessageResponse("Wrong Virtual Account, Please" +
                    " try again","400"), HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(new MessageResponse("Transaction Successfully","200"),HttpStatus.OK);
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        DBFile dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

}
