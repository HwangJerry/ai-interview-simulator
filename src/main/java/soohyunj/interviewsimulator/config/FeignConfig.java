package soohyunj.interviewsimulator.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import soohyunj.interviewsimulator.client.kakaotoken.KakaoTokenClient;
import soohyunj.interviewsimulator.client.chatgpt.ChatGptClient;

@Configuration
@EnableFeignClients(basePackageClasses = {ChatGptClient.class, KakaoTokenClient.class})
public class FeignConfig {
}
