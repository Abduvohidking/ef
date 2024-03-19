package uz.authorizationapp.service;

import uz.authorizationapp.upload.ApiResponse;
import uz.authorizationapp.upload.BattleDto;

public interface BattleService {
    public ApiResponse createBattle(BattleDto battleDto);
}
