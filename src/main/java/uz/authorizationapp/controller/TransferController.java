package uz.authorizationapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.authorizationapp.service.impl.TransferServiceImpl;
import uz.authorizationapp.upload.ApiResponse;
import uz.authorizationapp.upload.TransferDto;

@RestController
@RequestMapping("/api/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferServiceImpl transferService;

    @PostMapping(path = "create")
    public HttpEntity<?> transfer(@RequestBody TransferDto transferDto) {
        ApiResponse transfer = transferService.createTransfer(transferDto);
        return ResponseEntity.status(transfer.isSuccess() ? 200 : 400).body(transfer);
    }

}
