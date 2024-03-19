package uz.authorizationapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.authorizationapp.entity.Titles;

import java.util.List;

public interface TitleRepository extends JpaRepository<Titles,Long> {

    @Query("select new map (t.id as id , t.name as name, t.team.name as teamName)from Titles t where t.team.id = ?1")
    List<Object> findByTeamId(Long teamId);
}
