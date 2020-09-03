package ru.team42.analyzer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.team42.analyzer.dto.converters.ChannelDtoToChannelEntityConverter;
import ru.team42.analyzer.dto.converters.ChannelEntityToChannelDtoConverter;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ChannelDtoToChannelEntityConverter());
        registry.addConverter(new ChannelEntityToChannelDtoConverter());
    }

}
