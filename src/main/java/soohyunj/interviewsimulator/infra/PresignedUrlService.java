package soohyunj.interviewsimulator.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import soohyunj.interviewsimulator.redis.SimulationLogRedisRepository;
import soohyunj.interviewsimulator.util.SecurityUtil;

import java.util.List;
import java.util.stream.IntStream;

@Service
@Primary
@Slf4j
@RequiredArgsConstructor
public class PresignedUrlService {
    private final PresignedUrlGenerator presignedUrlGenerator;
    private final SimulationLogRedisRepository simulationLogRedisRepository;

    public AudioUploadUrlResponse getAudioUploadUrl(String fileNameExtension, Integer fileOrder) throws JsonProcessingException {
        String fileUrl = createAudioFileUrl(fileNameExtension, fileOrder);
        String presignedUrl = presignedUrlGenerator.createFileUploadUrl(fileUrl);
        return AudioUploadUrlResponse.of(presignedUrl, fileUrl);
    }

    public AudioReadUrlResponse getAudioReadUrls(String simulationLogId, Integer simulationSize,String fileNameExtension) {
        List<String> audios = IntStream.range(0, simulationSize)
                .mapToObj(i -> {
                    String fileUrl = getAudioFileUrl(fileNameExtension, i, simulationLogId);
                    return presignedUrlGenerator.createFileReadUrl(fileUrl);
                }).toList();
        return AudioReadUrlResponse.of(audios);
    }

    private String getAudioFileUrl(String fileNameExtension, Integer fileOrder, String simulationLogId) {
        return FileType.AUDIO + "/"
                + SecurityUtil.getLoginMemberId() + "/"
                + simulationLogId + "-" + fileOrder
                + "." + fileNameExtension;
    }

    private String createAudioFileUrl(String fileNameExtension, Integer fileOrder) throws JsonProcessingException {
        String loginMemberId = SecurityUtil.getLoginMemberId();
        String serialized = simulationLogRedisRepository.findById(loginMemberId).orElseThrow(() -> new IllegalArgumentException("InterviewLog not found"));// TODO : InterviewLog not found 예외 처리
        ObjectMapper mapper = new ObjectMapper();
        SimulationLogCache simulationLogCache = mapper.readValue(serialized, SimulationLogCache.class);
        return FileType.AUDIO + "/"
                + loginMemberId + "/"
                + simulationLogCache.interviewLogId() + "-" + fileOrder
                + "." + fileNameExtension;
    }
}
