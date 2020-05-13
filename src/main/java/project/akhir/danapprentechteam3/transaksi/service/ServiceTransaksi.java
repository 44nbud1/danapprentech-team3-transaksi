package project.akhir.danapprentechteam3.transaksi.service;

import project.akhir.danapprentechteam3.transaksi.model.Transaksi;

public interface ServiceTransaksi
{
    Transaksi saveTransaksi(Transaksi transaksi);
    boolean statusPembayaran(String statusPembayaran);
    Transaksi findByNoTelepon(String noTelepon);
}
