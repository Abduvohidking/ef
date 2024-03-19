package uz.authorizationapp.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.authorizationapp.entity.Players;
import uz.authorizationapp.entity.Team;
import uz.authorizationapp.entity.Transfers;
import uz.authorizationapp.repository.PlayersRepository;
import uz.authorizationapp.repository.TeamRepository;
import uz.authorizationapp.repository.TransferRepository;
import uz.authorizationapp.service.TransferService;
import uz.authorizationapp.upload.ApiResponse;
import uz.authorizationapp.upload.TransferDto;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final PlayersRepository playersRepository;
    private final TeamRepository teamRepository;
    private final TransferRepository transferRepository;

    @Transactional
    @Override
    public ApiResponse createTransfer(TransferDto transferDto) {
        if (!teamRepository.existsByName(transferDto.getFrom()))
            return new ApiResponse(false, transferDto.getFrom() + " team not found");
        if (!teamRepository.existsByName(transferDto.getTo()))
            return new ApiResponse(false, transferDto.getTo() + " team not found");
        if (!playersRepository.existsByUsername(transferDto.getUsername()))
            return new ApiResponse(false, transferDto.getUsername() + " username already exists");
        Players player = playersRepository.findByUsername(transferDto.getUsername());
        Team toTeam = teamRepository.findByName(transferDto.getTo());
        Team fromTeam = teamRepository.findByName(transferDto.getFrom());
        player.setTeam(toTeam);
        playersRepository.save(player);
        Transfers teamTransfers = new Transfers();
        teamTransfers.setFromTeam(fromTeam);
        teamTransfers.setToTeam(toTeam);
        teamTransfers.setPlayer(player);
        teamTransfers.setFormName(transferDto.getFormName());
        transferRepository.save(teamTransfers);
        return new ApiResponse(true, "success");
    }
}
