package uz.authorizationapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.authorizationapp.entity.BattlePlayers;

@Repository
public interface BattlePlayersRepository extends JpaRepository<BattlePlayers,Long> {
}
