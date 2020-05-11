package project.akhir.danapprentechteam3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.akhir.danapprentechteam3.payload.request.UpdatePassword;

@Repository
public interface UpdatePasswordRepository extends JpaRepository<UpdatePassword,Long> {
}
