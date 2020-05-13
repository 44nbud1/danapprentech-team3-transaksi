package project.akhir.danapprentechteam3.transaksi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.akhir.danapprentechteam3.transaksi.model.Transaksi;

@Repository
public interface TransaksiRepository extends JpaRepository<Transaksi, Long>
{
    boolean existsByStatusPembayaran(boolean statusPembayaran);
    Transaksi findByNomorTeleponUser(String nomorTeleponUser);
}
