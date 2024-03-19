package uz.authorizationapp.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.authorizationapp.entity.Transfers;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfers, Long> {

    @Query("SELECT new map(f.id as id, f.player.username as name, f.fromTeam.name as fromName, f.toTeam.name as toName) FROM Transfers f WHERE f.fromTeam.id = ?1 or f.toTeam.id= ?2 ")
    List<Object> findByFromTeamIdOrToTeamId(Long fromTeam_id, Long toTeam_id, Pageable pageable);

}
