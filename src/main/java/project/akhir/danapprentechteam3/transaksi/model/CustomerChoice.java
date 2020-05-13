package project.akhir.danapprentechteam3.transaksi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerChoice
{
    private int id;
    private String namaProvider;
    private Long harga;
    private String paketData;

    /*
             "nama_provider": "SmartFren",
            "harga": "27000",
            "id": "1",
            "paket_data": "Paket-Internet-1GB"
     */
}
