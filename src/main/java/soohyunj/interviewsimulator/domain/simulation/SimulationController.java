package soohyunj.interviewsimulator.domain.simulation;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import soohyunj.interviewsimulator.web.dto.CommonResponse;
import soohyunj.interviewsimulator.util.SecurityUtil;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SimulationController {
    private final SimulationService simulationService;

    @Operation(summary = "시뮬레이션 플레이리스트 조회", description = """
            시뮬레이션 재생목록을 조회합니다.
            - 수동 테스트 완료
            """)
    @GetMapping("/simulations")
    public CommonResponse<List<SimulationListResponse>> findSimulationList() {
        List<SimulationListResponse> response = simulationService.findListsByMember(SecurityUtil.getLoginMemberId());
        return CommonResponse.ok(response, "시뮬레이션 전체 조회가 정상적으로 처리되었습니다.");
    }

    @Operation(summary = "시뮬레이션 플레이리스트 생성", description = """
            시뮬레이션 재생목록을 추가합니다.
            - 수동 테스트 완료
            """)
    @PostMapping("/simulations")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<?> createSimulationList(
            @RequestBody SimulationListCreateRequest request
    ) {
        simulationService.createSimulationList(request.toServiceRequest(), SecurityUtil.getLoginMemberId());
        return CommonResponse.created("시뮬레이션 플레이리스트가 생성되었습니다.");
    }

    @Operation(summary = "시뮬레이션 플레이리스트 삭제", description = """
            시뮬레이션 재생목록을 삭제합니다.
            - 수동 테스트 완료
            """)
    @DeleteMapping("/simulations/{simulationListId}")
    public CommonResponse<?> deleteSimulationList(
            @PathVariable String simulationListId
    ) {
        simulationService.deleteSimulationList(simulationListId, SecurityUtil.getLoginMemberId());
        return CommonResponse.ok("시뮬레이션 플레이리스트가 삭제되었습니다.");
    }
    @Operation(summary = "시뮬레이션 시작", description = """
            시뮬레이션을 시작합니다.
            """)
    @PostMapping("/simulations/start")
    public CommonResponse<SimulationStartResponse> startSimulation(
            @Valid @RequestBody SimulationStartRequest request
    ) {
        simulationService.startSimulation(request.toServiceRequest(), SecurityUtil.getLoginMemberId());
        return CommonResponse.created("시뮬레이션을 시작합니다.");
    }

    @Operation(summary = "시뮬레이션 진행", description = """
            시뮬레이션을 진행합니다.
            - 수동 테스트 완료
            """)
    @PostMapping("/simulations/execute")
    public CommonResponse<SimulationResponse> executeSimulation(
            @RequestBody SimulationExecuteRequest request
    ) throws JsonProcessingException {
        SimulationResponse response = simulationService.executeSimulation(request.toServiceRequest(), SecurityUtil.getLoginMemberId());
        return CommonResponse.ok(response, "시뮬레이션을 진행합니다.");
    }

    @Operation(summary = "시뮬레이션 기록 저장", description = """
            로그인 유저의 시뮬레이션 기록을 저장합니다.
            - 수동 테스트 완료
            """)
    @PostMapping("/simulations/log")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<?> saveSimulationLog() throws JsonProcessingException {
        simulationService.saveSimulationLog(SecurityUtil.getLoginMemberId());
        return CommonResponse.created("시뮬레이션 기록이 저장되었습니다.");
    }

    @Operation(summary = "시뮬레이션 기록 조회", description = """
            로그인 유저의 시뮬레이션 기록을 조회합니다.
            - 수동 테스트 완료
            """)
    @GetMapping("/simulations/log")
    public CommonResponse<List<SimulationLogResponse>> findSimulationLog() {
        List<SimulationLogResponse> response = simulationService.findSimulationLog(SecurityUtil.getLoginMemberId());
        return CommonResponse.ok(response, "시뮬레이션 기록을 조회합니다.");
    }
}
