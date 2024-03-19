package uz.authorizationapp.upload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerUpdateDto {
    private String newName;
    private String oldName;
}
