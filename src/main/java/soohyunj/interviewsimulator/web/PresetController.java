package soohyunj.interviewsimulator.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import soohyunj.interviewsimulator.web.dto.CommonResponse;
import soohyunj.interviewsimulator.domain.preset.PresetReadResponse;
import soohyunj.interviewsimulator.domain.preset.PresetService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Preset", description = "프리셋 API")
public class PresetController {
    private final PresetService presetService;

    @Operation(summary = "프리셋 조회", description = "프리셋을 조회합니다.")
    @GetMapping("/presets")
    public CommonResponse<Map<String, List<PresetReadResponse>>> findAllPreset() {
        Map<String, List<PresetReadResponse>> response = presetService.findAllPreset();
        return CommonResponse.ok(response, "프리셋 전체 조회가 정상적으로 처리되었습니다.");
    }
}
