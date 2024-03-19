package uz.authorizationapp.upload.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeamsBattle {
    private String teamName;
    private int count;
    private boolean win;
}
