package project.akhir.danapprentechteam3.transaksi.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.akhir.danapprentechteam3.transaksi.model.Transaksi;

@Repository
public interface ServiceTransaksiRepository extends JpaRepository<Transaksi,Long>
{
    Transaksi findByNomorTeleponUser(String noTelepon);
}
