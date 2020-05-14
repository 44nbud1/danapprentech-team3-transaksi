package project.akhir.danapprentechteam3.transaksi.uploadfile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.akhir.danapprentechteam3.transaksi.uploadfile.model.DBFile;

public interface DBFileRepository extends JpaRepository<DBFile, String> {
}
