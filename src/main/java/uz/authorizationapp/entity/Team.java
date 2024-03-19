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
public class Team {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String foundationDate;

    private int ranking;

    private String teamLogo;

    @Enumerated(value = EnumType.STRING)
    private Tir tir = Tir.E;

    private int matchCount;

    private int winMatchCount;

    private int point;

    private int goleScore;

    private int leftScore;

    private String formName;
}
