package ru.team42.analyzer.services.interfaces;

import ru.team42.analyzer.dto.HitRequest;
import ru.team42.analyzer.dto.response.ButtonApiSettingsResponse;

import java.util.List;

public interface ApiService {

    List<ButtonApiSettingsResponse> getConfig(List<String> buttonIds);

    void hit(HitRequest hit);
}
