package project.akhir.danapprentechteam3.paketdata.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprovider")
    private int idprovider;
    @Column(name = "nama_provider")
    private String nama_provider;
    @Column(name = "kartu")
    private String kartu;
    @Column(name = "data_paketan")
    private String data_paketan;
    @Column(name = "harga")
    private long harga;

}
