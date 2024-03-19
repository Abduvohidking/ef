package uz.authorizationapp.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.authorizationapp.entity.Players;
import uz.authorizationapp.enums.Tir;
import uz.authorizationapp.repository.PlayersRepository;
import uz.authorizationapp.service.PlayerService;
import uz.authorizationapp.upload.ApiResponse;
import uz.authorizationapp.upload.PlayerUpdateDto;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayersRepository playersRepository;


    @Transactional
    @Override
    public ApiResponse updateUsername(PlayerUpdateDto playerUpdateDto) {
        boolean username = playersRepository.existsByUsername(playerUpdateDto.getOldName());
        if (!username) return new ApiResponse(false, playerUpdateDto.getOldName() + " not found");
        Players players = playersRepository.findByUsername(playerUpdateDto.getOldName());
        players.setUsername(playerUpdateDto.getNewName());
        Players save = playersRepository.save(players);

        return new ApiResponse(true, "success");
    }

    @Transactional
    @Override
    public ApiResponse updatePlayerRankAndTir() {
        List<Players> allTeamsSorted = getAllPlayerSorted();
        for (int i = 0; i < allTeamsSorted.size(); i++) {
            Players players = allTeamsSorted.get(i);
            players.setRanking(i + 1);
            if (i < 21) {
                players.setTir(Tir.A);
            } else if ( i < 41) {
                players.setTir(Tir.B);
            } else if ( i < 61) {
                players.setTir(Tir.C);
            } else if ( i < 81) {
                players.setTir(Tir.D);
            } else {
                players.setTir(Tir.E);
            }
            playersRepository.save(players);
        }
        return new ApiResponse(true, "success");
    }
    public List<Players> getAllPlayerSorted() {
        List<Players> players = playersRepository.findAll();

        Comparator<Players> comparator1 = Comparator.comparing(Players::getPoint);
        comparator1 = comparator1.reversed();
        Comparator<Players> comparator2 = Comparator.comparing(players1 -> (players1.getGol_score() - players1.getLeft_score()));
        comparator2 = comparator2.reversed();
        players.sort(comparator1.thenComparing(comparator2));
        return players;
    }
}
