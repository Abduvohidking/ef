package uz.authorizationapp.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.authorizationapp.entity.Players;
import uz.authorizationapp.entity.Team;

import java.util.List;

public interface PlayersRepository extends JpaRepository<Players,Long> {
    boolean existsByUsernameAndTeam(String username, Team team);

    boolean existsByUsername(String username);

    Players findByUsername(String username);

    Long  countAllByTeam_Id(Long teamId);

    @Query("select new map (p.id as id, p.team.name as teamName,p.username as username )from Players p where p.team.id = ?1 order by p.ranking desc ")
    List<Object> findByTeamId(Long teamId, Pageable pageable);
}
