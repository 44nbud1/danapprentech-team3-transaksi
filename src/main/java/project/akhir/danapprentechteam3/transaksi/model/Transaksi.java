package project.akhir.danapprentechteam3.transaksi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaksi implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idtransaksi;

    @Column(name = "nama_user")
    private String namaUser;
    @Column(name = "nomor_telepon")
    private String nomorTeleponUser;
    @Column(name = "nomor_paket_data")
    private String nomorPaketData;
    @Column(name = "nama_provider")
    private String namaProvider;
    @Column(name = "paket_data")
    private String paketData;
    @Column(name = "harga")
    private Long harga;
    private Date tanggal;
    @Column(name = "pembayaran_melalui")
    private String pembayaranMelalui;
    @Column(name = "status_pembayaran")
    private boolean statusPembayaran = false;
    private Long saldoAkhir;

}
