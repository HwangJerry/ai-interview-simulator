package soohyunj.interviewsimulator.domain.preset;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PresetService {
    private final PresetQaRepository presetQaRepository;

    public Map<String, List<PresetReadResponse>> findAllPreset() {
        List<PresetQA> presetQaList = presetQaRepository.findAll();
        HashMap<String, List<PresetReadServiceResponse>> serviceResponse = new HashMap<>();
        List<String> dirs = presetQaList.stream()
                .map(i -> i.getPresetDir().getValue())
                .distinct()
                .toList();
        dirs.forEach(i ->
            serviceResponse.put(i, new ArrayList<>()));
        presetQaList.forEach(i ->
            serviceResponse.get(i.getPresetDir().getValue()).add(i.toDto()));

        HashMap<String, List<PresetReadResponse>> response = new HashMap<>();

        serviceResponse.forEach((key, value) -> {
            List<PresetReadResponse> list = value
                    .stream()
                    .map(PresetReadServiceResponse::toResponse)
                    .toList();
            response.put(key, list);
        });
        return response;
    }
}
