package uz.authorizationapp.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.authorizationapp.entity.Battles;
import uz.authorizationapp.entity.Team;

import java.util.List;

@Repository
public interface BattleRepository extends JpaRepository<Battles,Long> {


    @Query("SELECT new map (b.id as id,b.team1.name as team1, b.team2.name as team2,b.team1Score as team1Score ,b.team2Score as team2Score, b.winTeam.id as winId) FROM Battles b where b.team1.id = ?1 or b.team2.id = ?2")
    List<Object> findAllByTeam1IdOrTeam2Id(Long team1_id, Long team2_id, Pageable pageable);

    @Query("SELECT new map(b.id as id, (case when b.winTeam.id = ?1 then true else false end) as result) FROM Battles b WHERE b.team1.id = ?1 OR b.team2.id = ?1 ORDER BY b.id DESC")
    List<Object> findAllByTeamLastMatch(Long teamId);

}
