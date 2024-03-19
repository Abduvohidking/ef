package uz.authorizationapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.authorizationapp.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Long> {
    boolean existsByName(String teamName);
    Team findByName(String teamName);

    Page<Team> findAll(Pageable pageable);
    @Query("SELECT t FROM Team t WHERE LOWER(t.name) LIKE %:name%")
    Page<Team> findByNameContaining(String name, Pageable pageable);

}
