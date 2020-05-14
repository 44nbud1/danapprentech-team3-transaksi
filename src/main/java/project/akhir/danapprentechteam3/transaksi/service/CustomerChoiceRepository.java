package project.akhir.danapprentechteam3.transaksi.service;

import org.springframework.data.jpa.repository.JpaRepository;
import project.akhir.danapprentechteam3.transaksi.model.CustomerChoice;

public interface CustomerChoiceRepository extends JpaRepository<CustomerChoice,Long>
{
    CustomerChoice findByNoTelepon(String noTelepon);
    void deleteByNoTelepon(String noTelepon);
}
