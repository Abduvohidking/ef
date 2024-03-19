package uz.authorizationapp.upload;

import lombok.*;
import uz.authorizationapp.upload.template.FounderDto;
import uz.authorizationapp.upload.template.TitlesDto;
import uz.authorizationapp.upload.template.UsernamesDto;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamRegisterDto {

        public ArrayList<UsernamesDto> usernames;
        public String teamName;
        public String foundationDate;
        public ArrayList<TitlesDto> titles;
        public ArrayList<FounderDto> founders;
        public String teamLogo;
        public String formName;

}
