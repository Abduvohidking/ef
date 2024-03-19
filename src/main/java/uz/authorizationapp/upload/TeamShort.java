package uz.authorizationapp.upload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamShort {
    private Long id;
    private String teamName;
    private String teamLogo;
}
