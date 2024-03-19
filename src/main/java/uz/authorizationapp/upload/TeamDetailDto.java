package uz.authorizationapp.upload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.authorizationapp.entity.*;
import uz.authorizationapp.enums.Tir;
import uz.authorizationapp.upload.template.TeamLastResult;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDetailDto {
    private Long id;
    private String name;
    private String foundationDate;
    private int ranking;
    private String teamLogo;
    private Tir tir;
    private List<Object> teamLastResults;
    private List<Object> teamBattles;
    private List<Object> teamPlayers;
    private List<Object> teamTransfers;
    private List<Object> teamTitles;
    private List<Object> teamFounders;
}
