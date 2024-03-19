package uz.authorizationapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.authorizationapp.enums.Tir;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Players {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    private int point = 0;

    @Enumerated(value = EnumType.STRING)
    private Tir tir = Tir.E;

    private int ranking;

    private int winMatch = 0;

    private int gol_score = 0;

    private int left_score = 0;

    private int match_count = 0;

}
