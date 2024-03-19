package uz.authorizationapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.authorizationapp.service.impl.TeamServiceImpl;
import uz.authorizationapp.upload.ApiResponse;
import uz.authorizationapp.upload.TeamRegisterDto;

@RestController
@RequestMapping("api/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamServiceImpl teamService;

    @PostMapping("/create")
    public HttpEntity<?> createTeam(@RequestBody TeamRegisterDto teamRegisterDto) {
        ApiResponse team = teamService.createTeam(teamRegisterDto);
        return ResponseEntity.status(team.isSuccess() ? 200 : 400).body(team);
    }

    @PutMapping("/update/team/ranking")
    public HttpEntity<?> updateTeamRanking() {
        ApiResponse team = teamService.updateTeamRankAndTir();
        return ResponseEntity.status(team.isSuccess() ? 200 : 400).body(team);
    }


    @GetMapping
    public HttpEntity<?> getTeams(@RequestParam(value = "search", required = false) String search,
                                  @RequestParam int page,
                                  @RequestParam int pageSize) {
        ApiResponse team = teamService.getTeams(page, pageSize, search);
        return ResponseEntity.status(team.isSuccess() ? 200 : 400).body(team);
    }
    @GetMapping("/{id}")
    public HttpEntity<?> getTeamById(@PathVariable Long id, @RequestParam int page, @RequestParam int pageSize) {
        ApiResponse team = teamService.getTeamById(id,page,pageSize);
        return ResponseEntity.status(team.isSuccess()? 200 : 400).body(team);
    }

}
