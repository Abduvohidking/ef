package uz.authorizationapp.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.authorizationapp.entity.*;
import uz.authorizationapp.enums.Tir;
import uz.authorizationapp.mapper.TeamToDtoMap;
import uz.authorizationapp.repository.*;
import uz.authorizationapp.service.TeamService;
import uz.authorizationapp.upload.ApiResponse;
import uz.authorizationapp.upload.TeamDetailDto;
import uz.authorizationapp.upload.TeamRegisterDto;
import uz.authorizationapp.upload.TeamShort;
import uz.authorizationapp.upload.template.FounderDto;
import uz.authorizationapp.upload.template.TitlesDto;
import uz.authorizationapp.upload.template.UsernamesDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final PlayersRepository playersRepository;
    private final TitleRepository titleRepository;
    private final FounderRepository founderRepository;
    private final BattleRepository battleRepository;
    private final TransferRepository transferRepository;

    @Transactional
    @Override
    public ApiResponse createTeam(TeamRegisterDto dto) {
        if (dto == null) return new ApiResponse(false, "team is not null");
        if (teamRepository.existsByName(dto.getTeamName())) return new ApiResponse(false, "team already exists");
        Team team = new Team();
        team.setName(dto.getTeamName());
        team.setFoundationDate(dto.getFoundationDate());
        team.setFormName(dto.getFormName());
        team.setTeamLogo(dto.getTeamLogo());
        Team save = teamRepository.save(team);
        for (UsernamesDto username : dto.usernames) {
            if (playersRepository.existsByUsername(username.getUsername()))
                return new ApiResponse(false, username + " username already exists");
            Players player = new Players();
            player.setUsername(username.getUsername());
            player.setTeam(save);
            playersRepository.save(player);
        }
        for (TitlesDto title : dto.titles) {
            Titles titles = new Titles();
            titles.setName(title.getTitle());
            titles.setTeam(save);
            titleRepository.save(titles);
        }
        for (FounderDto founder : dto.founders) {
            if (founderRepository.existsByName(founder.getTitle()))
                return new ApiResponse(false, founder + " founder already exists");
            Founders found = new Founders();
            found.setName(founder.getTitle());
            found.setTeam(save);
            founderRepository.save(found);
        }
        Optional<Team> teamOptional = teamRepository.findById(save.getId());

        return new ApiResponse(true, "success", teamOptional.get());
    }


    @Override
    public ApiResponse getTeamById(Long id,int page,int pageSize) {
        Optional<Team> teamOptional = teamRepository.findById(id);
        if (!teamOptional.isPresent()) return new ApiResponse(false, "team not found", teamOptional.get());
        Pageable pageable = PageRequest.of(page,pageSize);
        Team team = teamOptional.get();

        List<Object> teamFounders = founderRepository.findByTeamId(team.getId());
        List<Object> teamBattles = battleRepository.findAllByTeam1IdOrTeam2Id(team.getId(), team.getId(),pageable);
        List<Object> teamPlayers = playersRepository.findByTeamId(team.getId(),pageable);
        List<Object> teamTitles = titleRepository.findByTeamId(team.getId());
        List<Object> teamTransfer = transferRepository.findByFromTeamIdOrToTeamId(team.getId(),team.getId(),pageable);
        List<Object> teamLastResults = battleRepository.findAllByTeamLastMatch(team.getId());
        TeamDetailDto teamDetailDto = new TeamDetailDto();
        teamDetailDto.setId(team.getId());
        teamDetailDto.setName(team.getName());
        teamDetailDto.setTeamFounders(teamFounders);
        teamDetailDto.setTeamLogo(team.getTeamLogo());
        teamDetailDto.setFoundationDate(team.getFoundationDate());
        teamDetailDto.setTeamBattles(teamBattles);
        teamDetailDto.setFoundationDate(team.getFoundationDate());
        teamDetailDto.setTir(team.getTir());
        teamDetailDto.setRanking(team.getRanking());
        teamDetailDto.setTeamPlayers(teamPlayers);
        teamDetailDto.setTeamTitles(teamTitles);
        teamDetailDto.setTeamLastResults(teamLastResults);
        teamDetailDto.setTeamTransfers(teamTransfer);
        return new ApiResponse(true,"success",teamDetailDto);
    }

    @Override
    public ApiResponse getTeams(int page, int pageSize, String search) {
        Page<Team> teams;
        List<TeamShort> teamShorts = new ArrayList<>();
        if (search == null) {
            Pageable pageable = PageRequest.of(page, pageSize, Sort.by("ranking"));
            teams = teamRepository.findAll(pageable);

        } else {
            Pageable pageable = PageRequest.of(page, pageSize);
            search = search.toLowerCase();
            teams = teamRepository.findByNameContaining(search, pageable);
        }
        for (Team team : teams) {
            TeamShort teamShort = TeamToDtoMap.mapToDto(team);
            teamShorts.add(teamShort);
        }
        return new ApiResponse(true, "success", teamShorts);
    }

    @Override
    public ApiResponse updateTeamRankAndTir() {
        List<Team> allTeamsSorted = getAllTeamsSorted();
        for (int i = 0; i < allTeamsSorted.size(); i++) {
            Team team = allTeamsSorted.get(i);
            team.setRanking(i + 1);
            if (i < 8) {
                team.setTir(Tir.A);
            } else if (i > 8 && i < 16) {
                team.setTir(Tir.B);
            } else if (i > 15 && i < 23) {
                team.setTir(Tir.C);
            } else if (i > 22 && i < 31) {
                team.setTir(Tir.D);
            } else {
                team.setTir(Tir.E);
            }
            teamRepository.save(team);
        }
        return new ApiResponse(true, "success");
    }


    public List<Team> getAllTeamsSorted() {
        List<Team> teams = teamRepository.findAll();

        Comparator<Team> comparator1 = Comparator.comparing(Team::getPoint);
        comparator1 = comparator1.reversed();
        Comparator<Team> comparator2 = Comparator.comparing(team -> (team.getGoleScore() - team.getLeftScore()));
        comparator2 = comparator2.reversed();
        teams.sort(comparator1.thenComparing(comparator2));
        return teams;
    }


}
