package uz.authorizationapp.service;

import uz.authorizationapp.upload.ApiResponse;
import uz.authorizationapp.upload.TransferDto;

public interface TransferService {
    ApiResponse createTransfer(TransferDto transferDto);
}
