package soohyunj.interviewsimulator.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import soohyunj.interviewsimulator.auth.AuthService;
import soohyunj.interviewsimulator.web.dto.LoginRequest;
import soohyunj.interviewsimulator.auth.dto.LoginToken;
import soohyunj.interviewsimulator.auth.dto.IdToken;
import soohyunj.interviewsimulator.auth.dto.TokenRefreshRequest;
import soohyunj.interviewsimulator.web.dto.CommonResponse;
import soohyunj.interviewsimulator.util.SecurityUtil;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Auth", description = "인증 API")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "id token 발급", description = "인가 코드로 id token을 발급받습니다.")
    @GetMapping("/{loginType}/id-token")
    public CommonResponse<IdToken> getIdToken(
            @RequestParam("code") String code,
            @PathVariable("loginType") String loginType
    ) {
        IdToken idToken = authService.getIdToken(loginType, code);
        return CommonResponse.ok(idToken);
    }

    @Operation(summary = "로그인", description = "id token과 login type으로 로그인 합니다.")
    @PostMapping("/{loginType}/login")
    public CommonResponse<LoginToken> login(
            @RequestBody IdToken idToken,
            @PathVariable("loginType") String loginType
    ) {
        LoginToken loginToken = authService.login(loginType, idToken);
        return CommonResponse.ok(loginToken);
    }

    @Operation(summary = "로그인v2", description = "유저 정보를 이용하여 login type으로 로그인 합니다.")
    @PostMapping("/{loginType}/login-v2")
    public CommonResponse<LoginToken> loginV2(
            @RequestBody LoginRequest request,
            @PathVariable("loginType") String loginType
    ) {
        LoginToken loginToken = authService.loginV2(request);
        return CommonResponse.ok(loginToken);
    }

    @Operation(summary = "토큰 갱신", description = "refresh token으로 access token을 갱신합니다.")
    @PostMapping("/refresh")
    public CommonResponse<LoginToken> refresh(
            @RequestBody TokenRefreshRequest request
    ) {
        LoginToken loginToken = authService.refresh(request);
        return CommonResponse.ok(loginToken);
    }

    @Operation(summary = "ping", description = "인증 서버가 정상적으로 동작하는지 확인합니다.")
    @GetMapping("/ping")
    public CommonResponse<?> ping() {
        log.debug("loginMemberId : {}", SecurityUtil.getLoginMemberId());
        return CommonResponse.ok("pong");
    }
}
