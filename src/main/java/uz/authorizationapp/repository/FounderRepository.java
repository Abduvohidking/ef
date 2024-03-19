package uz.authorizationapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.authorizationapp.entity.Founders;

import java.util.List;

public interface FounderRepository extends JpaRepository<Founders,Long> {
    boolean existsByName(String founderName);

    @Query("SELECT new map(f.id as id, f.name as name, f.team.name as teamName) FROM Founders f WHERE f.id = ?1")
    List<Object> findByTeamId(Long teamId);
}
