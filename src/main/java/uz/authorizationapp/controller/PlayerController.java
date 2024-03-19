package uz.authorizationapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.authorizationapp.service.impl.PlayerServiceImpl;
import uz.authorizationapp.upload.ApiResponse;
import uz.authorizationapp.upload.PlayerUpdateDto;

@RestController
@RequestMapping("api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerServiceImpl playerService;

    @PutMapping("/change/username")
    public HttpEntity<?> changeUsername(@RequestBody PlayerUpdateDto playerUpdateDto) {
        ApiResponse response = playerService.updateUsername(playerUpdateDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }

    @PutMapping("/update/ranking")
    public HttpEntity<?> updatePlayerRanking() {
        ApiResponse team = playerService.updatePlayerRankAndTir();
        return ResponseEntity.status(team.isSuccess()? 200:400).body(team);
    }
}
