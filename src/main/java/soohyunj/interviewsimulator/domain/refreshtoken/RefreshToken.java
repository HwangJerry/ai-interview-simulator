package soohyunj.interviewsimulator.domain.refreshtoken;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken{
    private String memberId;
    private String token;
}
