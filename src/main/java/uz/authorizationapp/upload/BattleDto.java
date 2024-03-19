package uz.authorizationapp.upload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.authorizationapp.upload.template.PlayerBattleDto;
import uz.authorizationapp.upload.template.TeamsBattle;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BattleDto {
    private ArrayList<TeamsBattle> teams;
    private ArrayList<ArrayList<PlayerBattleDto>> players;
    private String battleType;
    private String formName;

}
