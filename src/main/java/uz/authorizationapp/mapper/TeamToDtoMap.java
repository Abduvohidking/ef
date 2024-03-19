package uz.authorizationapp.mapper;

import uz.authorizationapp.entity.Team;
import uz.authorizationapp.upload.TeamShort;

public class TeamToDtoMap {
    public static TeamShort mapToDto(Team team){
        TeamShort teamShort = new TeamShort();
        teamShort.setId(team.getId());
        teamShort.setTeamName(team.getName());
        teamShort.setTeamLogo(team.getTeamLogo());
        return teamShort;
    }
}
