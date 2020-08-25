package ru.team42.analyzer.services.interfaces;

import ru.team42.analyzer.dto.ChannelRenderSetting;

import java.util.List;

public interface AppService {

    List<ChannelRenderSetting> config(List<Long> channels);
    List<ChannelRenderSetting> configByJsClasses(List<String> channels);

    String hit(String channelId, String data);
}
