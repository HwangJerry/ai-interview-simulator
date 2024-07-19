package soohyunj.interviewsimulator.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import soohyunj.interviewsimulator.web.dto.CommonResponse;
import soohyunj.interviewsimulator.util.SecurityUtil;
import soohyunj.interviewsimulator.domain.chatgpt.service.ChatGptService;
import soohyunj.interviewsimulator.domain.interview.*;
import soohyunj.interviewsimulator.web.dto.CreateAnswerRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "interview list API", description = "면접리스트 API")
public class InterviewListController {
    private final MemberQaService memberQaService;
    private final ChatGptService chatGptService;

    @Operation(summary = "멤버 면접리스트 생성", description = "멤버 면접리스트를 생성합니다.")
    @PostMapping("/interviews")
    @ResponseStatus(HttpStatus.CREATED) // TODO : AOP로 변경
    public CommonResponse<?> createMemberQa(
            @RequestBody MemberQaCreateRequest request
    ) {
        memberQaService.createCustomMemberQa(request.toServiceDto());
        return CommonResponse.created("멤버 면접리스트가 정상적으로 생성되었습니다.");
    }

    @Operation(summary = "멤버 면접리스트 수정", description = "멤버 면접리스트를 수정합니다.")
    @PutMapping("/interviews/{id}")
    public CommonResponse<MemberQaCommandResponse> updateMemberQa(
            @PathVariable(name="id") String id,
            @RequestBody MemberQaUpdateRequest request
    ) {
        MemberQaCommandResponse response = memberQaService.updateMemberQa(request.toServiceDto(id)).toResponseDto();
        return CommonResponse.ok(response,"멤버 면접리스트가 정상적으로 수정되었습니다.");
    }

    @Operation(summary = "멤버 면접리스트 삭제", description = "멤버 면접리스트를 삭제합니다.")
    @DeleteMapping("/interviews/{id}")
    public CommonResponse<MemberQaCommandResponse> deleteMemberQa(
            @PathVariable(name="id") String id
    ) {
        MemberQaCommandResponse response = memberQaService.deleteMemberQa(MemberQaDeleteServiceRequest.of(id)).toResponseDto();
        return CommonResponse.ok(response,"멤버 면접리스트가 정상적으로 삭제되었습니다.");
    }

    @Operation(summary = "멤버 면접리스트 전체 조회", description = "멤버 면접리스트를 조회합니다.")
    @GetMapping("/interviews")
    public CommonResponse<List<MemberQaReadResponse>> findAllMemberQa() {
        List<MemberQaReadResponse> response = memberQaService.findAllMemberQa(SecurityUtil.getLoginMemberId());
        return CommonResponse.ok(response,"멤버 면접리스트 전체 조회가 정상적으로 처리되었습니다.");
    }

    @Operation(summary = "면접리스트 모범정답 생성", description = "AI를 통해 면접리스트의 모범답변을 생성합니다.")
    @PostMapping("/interviews/answer")
    public CommonResponse<Map<String, Object>> createAnswer(
            @RequestBody CreateAnswerRequest request
    ) {
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessage = chatGptService.getUserMessage(request.question());
        messages.add(userMessage);
        String response = chatGptService.getResponse(messages);
        return CommonResponse.ok(Map.of("answer", response), "AI를 이용한 모범 답변이 제공되었습니다.");
    }
}
