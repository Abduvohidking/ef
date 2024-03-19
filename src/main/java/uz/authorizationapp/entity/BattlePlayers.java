package uz.authorizationapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BattlePlayers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Battles battle;

    @ManyToOne
    private Players player1;

    @ManyToOne
    private Players player2;

    private int player1Score;

    private int player2Score;

    @ManyToOne
    private Players winner;



}
