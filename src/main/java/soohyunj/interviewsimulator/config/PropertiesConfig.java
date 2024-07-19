package soohyunj.interviewsimulator.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import soohyunj.interviewsimulator.auth.properties.JwtProperties;
import soohyunj.interviewsimulator.auth.properties.KakaoProperties;
import soohyunj.interviewsimulator.domain.chatgpt.ChatGptProperties;
import soohyunj.interviewsimulator.infra.S3Properties;

@Configuration
@EnableConfigurationProperties({
        KakaoProperties.class,
        JwtProperties.class,
        ChatGptProperties.class,
        S3Properties.class
})
public class PropertiesConfig {
}
