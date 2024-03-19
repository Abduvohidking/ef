package uz.authorizationapp.upload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {
    private String username;
    private String from;
    private String to;
    private String formName;
}
