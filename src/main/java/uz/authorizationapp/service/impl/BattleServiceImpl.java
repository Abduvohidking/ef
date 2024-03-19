package uz.authorizationapp.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.authorizationapp.entity.BattlePlayers;
import uz.authorizationapp.entity.Battles;
import uz.authorizationapp.entity.Players;
import uz.authorizationapp.entity.Team;
import uz.authorizationapp.repository.BattlePlayersRepository;
import uz.authorizationapp.repository.BattleRepository;
import uz.authorizationapp.repository.PlayersRepository;
import uz.authorizationapp.repository.TeamRepository;
import uz.authorizationapp.service.BattleService;
import uz.authorizationapp.upload.ApiResponse;
import uz.authorizationapp.upload.BattleDto;
import uz.authorizationapp.upload.template.PlayerBattleDto;
import uz.authorizationapp.upload.template.TeamsBattle;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {

    private final BattleRepository battleRepository;
    private final BattlePlayersRepository battlePlayersRepository;
    private final TeamRepository teamRepository;
    private final PlayersRepository playersRepository;
    private final TeamServiceImpl teamService;
    private final PlayerServiceImpl playersService;

    @Transactional
    @Override
    public ApiResponse createBattle(BattleDto battleDto) {
        if (battleDto.getTeams().size() != 2) return new ApiResponse(false, "team size = 2 required");
        TeamsBattle dtoTeam1 = battleDto.getTeams().get(0);
        TeamsBattle dtoTeam2 = battleDto.getTeams().get(1);
        if (!teamRepository.existsByName(dtoTeam1.getTeamName()))
            return new ApiResponse(false, "team name = " + dtoTeam1.getTeamName() + " not found");
        Team team1 = teamRepository.findByName(dtoTeam1.getTeamName());

        if (!teamRepository.existsByName(dtoTeam2.getTeamName()))
            return new ApiResponse(false, "team name = " + dtoTeam2.getTeamName() + " not found");
        Team team2 = teamRepository.findByName(dtoTeam2.getTeamName());

        //====== Battle entity write ====== //
        Battles battles = new Battles();
        battles.setTeam1(team1);
        battles.setTeam2(team2);
        battles.setTeam1Score(dtoTeam1.getCount());
        battles.setTeam2Score(dtoTeam2.getCount());
        battles.setBattleType(battleDto.getBattleType());
        battles.setFormName(battleDto.getFormName());
        if (dtoTeam1.isWin()) {
            battles.setWinTeam(team1);
        }
        if (dtoTeam2.isWin()) {
            battles.setWinTeam(team2);
        }
        battleRepository.save(battles);

        // ===== Team entity update ======= //
        team1.setGoleScore(dtoTeam1.getCount());
        team1.setLeftScore(dtoTeam2.getCount());
        team1.setMatchCount(team1.getMatchCount() + 1);
        if (dtoTeam1.isWin()) {
            team1.setWinMatchCount(team1.getWinMatchCount() + 1);
            team1.setPoint(team1.getPoint() + 3);
        }
        teamRepository.save(team1);

        team2.setGoleScore(dtoTeam2.getCount());
        team2.setLeftScore(dtoTeam1.getCount());
        team2.setMatchCount(team2.getMatchCount() + 1);
        if (dtoTeam2.isWin()) {
            team2.setWinMatchCount(team2.getWinMatchCount() + 1);
            team2.setPoint(team2.getPoint() + 3);
        }
        teamRepository.save(team2);
        boolean success = battlePlayer(battleDto.getPlayers(), battles);
        if (!success) {
            return new ApiResponse(false, "error battlePlayer failed");
        }
        teamService.updateTeamRankAndTir();
        playersService.updatePlayerRankAndTir();
        return new ApiResponse(true, "success", battles);
    }

    public boolean battlePlayer(ArrayList<ArrayList<PlayerBattleDto>> dto, Battles battles) {
        for (ArrayList<PlayerBattleDto> battleDtos : dto) {
            if (battleDtos.size() != 2) return false;
            PlayerBattleDto player1Dto = battleDtos.get(0);
            PlayerBattleDto player2Dto = battleDtos.get(1);
            if (!playersRepository.existsByUsernameAndTeam(player1Dto.getUsername(),battles.getTeam1())) return false;
            if (!playersRepository.existsByUsernameAndTeam(player2Dto.getUsername(),battles.getTeam2())) return false;
            Players player1 = playersRepository.findByUsername(player1Dto.getUsername());
            Players player2 = playersRepository.findByUsername(player2Dto.getUsername());

            // ===== battlePlayer save player ==== //
            BattlePlayers battlePlayers = new BattlePlayers();
            battlePlayers.setBattle(battles);
            battlePlayers.setPlayer1(player1);
            battlePlayers.setPlayer2(player2);
            battlePlayers.setPlayer1Score(player1Dto.getCount());
            battlePlayers.setPlayer2Score(player2Dto.getCount());
            if (player1Dto.isWin()) {
                battlePlayers.setWinner(player1);
            } else {
                battlePlayers.setWinner(player2);
            }
            battlePlayersRepository.save(battlePlayers);

            // ===== Player entity update ======= //

            player1.setGol_score(player1Dto.getCount());
            player1.setLeft_score(player2Dto.getCount());
            player1.setMatch_count(player1.getMatch_count() + 1);
            if (player1Dto.isWin()) {
                player1.setWinMatch(player1.getWinMatch() + 1);
                player1.setPoint(player1.getPoint() + 3);
            }

            playersRepository.save(player1);

            player2.setGol_score(player2Dto.getCount());
            player2.setLeft_score(player1Dto.getCount());
            player2.setMatch_count(player2.getMatch_count() + 1);
            if (player2Dto.isWin()) {
                player2.setWinMatch(player2.getWinMatch() + 1);
                player2.setPoint(player2.getPoint() + 3);
            }
            playersRepository.save(player2);


        }

        return true;
    }


}
