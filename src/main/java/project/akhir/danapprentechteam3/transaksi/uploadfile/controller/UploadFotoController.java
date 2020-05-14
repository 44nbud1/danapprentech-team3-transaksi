package project.akhir.danapprentechteam3.transaksi.uploadfile.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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


@RestController
@RequestMapping("/api/transaksi-upload-photo")
public class UploadFotoController {
    private static final Logger logger = LoggerFactory.getLogger(UploadFotoController.class);

    @Autowired
    private DBFileStorageService dbFileStorageService;

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

    private boolean statusUpload;

//    @ResponseBody
    @PostMapping("/buktipembayaran")
    private ResponseEntity uploadFileResponse(@RequestPart("file") MultipartFile file)
    {
        DBFile dbFile = dbFileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/transaksi-upload-photo/downloadFile/")
                .path(String.valueOf(dbFile.getId()))
                .toUriString();


        if (fileDownloadUri == null)
        {
            System.out.println("failed");
            UploadFileResponse files = new UploadFileResponse();
            files.setMessage("Transaction failed , please try again");
            files.setMessage("400");
            return ResponseEntity.ok(new MessageResponse("Transacstion failed","400"));
        }

        return ResponseEntity.ok(new MessageResponse("Transacstion successfully","200"));
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
