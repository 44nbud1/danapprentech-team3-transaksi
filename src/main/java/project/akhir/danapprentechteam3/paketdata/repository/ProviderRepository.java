package project.akhir.danapprentechteam3.paketdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.akhir.danapprentechteam3.paketdata.model.Provider;

import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<Provider,Integer> {
    List<Provider> findByKartu(String kartu);

}
