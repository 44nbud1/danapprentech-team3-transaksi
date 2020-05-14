package project.akhir.danapprentechteam3.transaksi.model.transaksirequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransaksiRequest
{
    private String noTelepon;
    private String virtualAccount;
    private Long pinTransaksi;

}
