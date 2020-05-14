package project.akhir.danapprentechteam3.transaksi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerChoice
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;
    private String namaProvider;
    private Long harga;
    private String paketData;
    private String noTelepon;
    @CreatedDate
    private Date waktuTransaksi;
    private boolean statusTransaksi = false;
    private String nomorPaketData;

    

    /*
             "nama_provider": "SmartFren",
            "harga": "27000",
            "id": "1",
            "paket_data": "Paket-Internet-1GB"
     */
}
