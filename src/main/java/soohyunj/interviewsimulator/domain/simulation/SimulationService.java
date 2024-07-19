package soohyunj.interviewsimulator.domain.simulation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soohyunj.interviewsimulator.exception.InvalidLoginInfoException;
import soohyunj.interviewsimulator.redis.SimulationLogRedisRepository;
import soohyunj.interviewsimulator.domain.chatgpt.service.ChatGptService;
import soohyunj.interviewsimulator.domain.interview.MemberQaRepository;
import soohyunj.interviewsimulator.domain.interview.NotFoundMemberQaException;
import soohyunj.interviewsimulator.domain.member.Member;
import soohyunj.interviewsimulator.domain.member.MemberRepository;
import soohyunj.interviewsimulator.infra.SimulationLogCache;
import soohyunj.interviewsimulator.infra.SimulationLog;

import java.util.*;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SimulationService {

    private final SimulationListRepository simulationListRepository;
    private final SimulationQARepository simulationQARepository;
    private final SimulationHistoryRepository simulationHistoryRepository;
    private final MemberRepository memberRepository;
    private final ChatGptService chatGptService;
    private final SimulationLogRedisRepository simulationLogRedisRepository;
    private final MemberQaRepository memberQaRepository;

    public List<SimulationListResponse> findListsByMember(String memberId) {
        Member loginMember = memberRepository.findById(memberId)
                .orElseThrow(InvalidLoginInfoException::new);
        List<SimulationList> simulationLists = simulationListRepository.findByMember(loginMember);

        List<SimulationListResponse> response = new ArrayList<>();

        simulationLists.forEach(simulationList -> {
            List<SimulationQA> bySimulationList = simulationQARepository.findBySimulationList(simulationList);
            List<SimulationReadResponse> payload = new ArrayList<>();
            bySimulationList.stream()
                    .filter(simulationQA -> simulationQA.getSimulationList().getId().equals(simulationList.getId()))
                    .forEach(simulationQA -> {
                        payload.add(simulationQA.toReadResponse());
            });

            response.add(new SimulationListResponse(simulationList.getId(), simulationList.getName(), payload));
        });

        return response;
    }

    @Transactional
    public void startSimulation(SimulationStartServiceRequest request, String loginMemberId) {
        ArrayList<SimulationLog> simulationLogs = new ArrayList<>();
        IntStream.range(0, request.simulationListSize())
                .forEach(i -> simulationLogs.add(new SimulationLog()));
        SimulationLogCache simulationLogCache = new SimulationLogCache(request.simulationListName(), UUID.randomUUID().toString(), simulationLogs);
        simulationLogRedisRepository.save(loginMemberId, simulationLogCache.serialized());
    }

    public SimulationResponse executeSimulation(SimulationServiceRequest request, String loginMemberId) throws JsonProcessingException {
        // get feedback
        String prompt = getPrompt(request);

        // save log with feedback
        ObjectMapper mapper = new ObjectMapper();
        String serialized = simulationLogRedisRepository.findById(loginMemberId).orElseThrow(NotFoundSimulationLogException::new);
        SimulationLogCache simulationLogCache = mapper.readValue(serialized, SimulationLogCache.class);

        List<SimulationLog> simulationLogs = simulationLogCache.simulationLogs();
        SimulationLog newLog = new SimulationLog(request, prompt);
        List<SimulationLog> subLogs = simulationLogs.subList(0, request.order());
        if (!subLogs.isEmpty()) {
            subLogs.removeLast();
        }
        subLogs.add(newLog);
        subLogs.addAll(simulationLogs.subList(request.order(), simulationLogs.size()));

        // update cache
        SimulationLogCache updated = new SimulationLogCache(simulationLogCache.simulationListName(), simulationLogCache.interviewLogId(), subLogs);
        simulationLogRedisRepository.save(loginMemberId, updated.serialized());
        return new SimulationResponse(prompt);
    }

    private String getPrompt(SimulationServiceRequest request) {
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessage = chatGptService.getUserMessage(request.getRequestPrompt());
        messages.add(userMessage);
        return chatGptService.getResponse(messages);
    }

    @Transactional
    public void saveSimulationLog(String loginMemberId) throws JsonProcessingException {
        Member loginMember = memberRepository.findById(loginMemberId)
                .orElseThrow(NotFoundMemberException::new);

        String serialized = simulationLogRedisRepository.findById(loginMemberId)
                .orElseThrow(NotFoundSimulationLogException::new);

        ObjectMapper mapper = new ObjectMapper();

        SimulationLogCache simulationLogCache = mapper.readValue(serialized, SimulationLogCache.class);
        SimulationHistory simulationHistory = new SimulationHistory(simulationLogCache, loginMember);
        simulationHistoryRepository.save(simulationHistory);
    }

    public List<SimulationLogResponse> findSimulationLog(String loginMemberId) {
        Member loginMember = memberRepository.findById(loginMemberId)
                .orElseThrow(NotFoundMemberException::new);
        List<SimulationHistory> simulationHistories = simulationHistoryRepository.findByMember(loginMember);
        return simulationHistories.stream()
                .map(SimulationHistory::toResponse)
                .toList();
    }

    @Transactional
    public void createSimulationList(SimulationListCreateServiceRequest serviceRequest, String loginMemberId) {
        Member loginMember = memberRepository.findById(loginMemberId)
                .orElseThrow(NotFoundMemberException::new);
        if (simulationListRepository.existsByName(serviceRequest.simulationListName())) {
            throw new DuplicateSimulationNameException();
        }
        SimulationList simulationList = simulationListRepository.save(new SimulationList(serviceRequest.simulationListName(), loginMember));
        serviceRequest.memberQAIds().stream()
                .map(memberQAId -> memberQaRepository.findById(memberQAId)
                        .orElseThrow(NotFoundMemberQaException::new))
                .forEach(memberQA -> simulationQARepository.save(new SimulationQA(memberQA, simulationList)));
    }

    @Transactional
    public void deleteSimulationList(String simulationListId, String loginMemberId) {
        Member loginMember = memberRepository.findById(loginMemberId)
                .orElseThrow(NotFoundMemberException::new);
        SimulationList simulationList = simulationListRepository.findById(simulationListId)
                .orElseThrow(() -> new IllegalArgumentException("시뮬레이션 리스트가 존재하지 않습니다."));
        if (!simulationList.getMember().equals(loginMember)) {
            throw new IllegalArgumentException("시뮬레이션 리스트에 접근할 수 없습니다.");
        }
        simulationQARepository.deleteBySimulationList(simulationList);
        simulationListRepository.delete(simulationList);
    }
}
