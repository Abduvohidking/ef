package uz.authorizationapp.service;

import uz.authorizationapp.upload.ApiResponse;
import uz.authorizationapp.upload.TeamRegisterDto;

public interface TeamService {
    ApiResponse createTeam(TeamRegisterDto teamRegisterDto);
    ApiResponse updateTeamRankAndTir();
    ApiResponse getTeams(int page, int pageSize,String search);
    ApiResponse getTeamById(Long id,int page,int pageSize);
}
