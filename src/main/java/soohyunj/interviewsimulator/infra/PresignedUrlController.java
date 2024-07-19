package soohyunj.interviewsimulator.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import soohyunj.interviewsimulator.web.dto.CommonResponse;

@RestController
@RequiredArgsConstructor
public class PresignedUrlController {
    private final PresignedUrlService presignedUrlService;

    @Operation(summary = "audio 파일 업로드용입니다.")
    @PostMapping("/audio")
    public CommonResponse<AudioUploadUrlResponse> getAudioUploadUrl(
            @RequestParam("fileExtension") String fileExtension,
            @RequestParam("fileOrder") Integer fileOrder
    ) throws JsonProcessingException {
        return CommonResponse.ok(presignedUrlService.getAudioUploadUrl(fileExtension, fileOrder));
    }

    @Operation(summary = "audio 파일 조회용입니다.")
    @GetMapping("/audio")
    public CommonResponse<AudioReadUrlResponse> getAudioReadUrls(
            @RequestParam("simulationLogId") String simulationLogId,
            @RequestParam("simulationSize") Integer simulationSize,
            @RequestParam("fileNameExtension") String fileNameExtension
    ) {
        return CommonResponse.ok(presignedUrlService.getAudioReadUrls(simulationLogId, simulationSize, fileNameExtension));
    }

}
