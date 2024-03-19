package uz.authorizationapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.authorizationapp.service.impl.BattleServiceImpl;
import uz.authorizationapp.upload.ApiResponse;
import uz.authorizationapp.upload.BattleDto;

@RestController
@RequestMapping("/api/battle")
@RequiredArgsConstructor
public class BattleController {
    private  final BattleServiceImpl battleService;
    @PostMapping("/create")
    public HttpEntity<?> createBattle(@RequestBody BattleDto battleDto) {
        ApiResponse battle = battleService.createBattle(battleDto);
        return ResponseEntity.status(battle.isSuccess() ? 200 : 400).body(battle);
    }

}
