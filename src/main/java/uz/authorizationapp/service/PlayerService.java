package uz.authorizationapp.service;

import uz.authorizationapp.upload.ApiResponse;
import uz.authorizationapp.upload.PlayerUpdateDto;

public interface PlayerService {
    ApiResponse updateUsername(PlayerUpdateDto playerUpdateDto);
    ApiResponse updatePlayerRankAndTir();
}
