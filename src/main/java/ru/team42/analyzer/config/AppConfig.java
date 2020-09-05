package ru.team42.analyzer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.team42.analyzer.dto.converters.ButtonDtoToButtonEntityConverter;
import ru.team42.analyzer.dto.converters.ButtonEntityToButtonDtoConverter;
import ru.team42.analyzer.dto.converters.ChannelDtoToChannelEntityConverter;
import ru.team42.analyzer.dto.converters.ChannelEntityToChannelDtoConverter;
import ru.team42.analyzer.dto.converters.HitDtoToHitEntityConverter;
import ru.team42.analyzer.dto.converters.HitEntityToHitDtoConverter;
import ru.team42.analyzer.dto.converters.IntegrationDtoToIntegrationEntityConverter;
import ru.team42.analyzer.dto.converters.IntegrationEntityToIntegrationDtoConverter;
import ru.team42.analyzer.dto.converters.MessengerDtoToMessengerEntityConverter;
import ru.team42.analyzer.dto.converters.MessengerEntityToMessengerDtoConverter;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ChannelDtoToChannelEntityConverter());
        registry.addConverter(new ChannelEntityToChannelDtoConverter());

        registry.addConverter(new ButtonDtoToButtonEntityConverter());
        registry.addConverter(new ButtonEntityToButtonDtoConverter());

        registry.addConverter(new HitDtoToHitEntityConverter());
        registry.addConverter(new HitEntityToHitDtoConverter());

        registry.addConverter(new IntegrationDtoToIntegrationEntityConverter());
        registry.addConverter(new IntegrationEntityToIntegrationDtoConverter());

        registry.addConverter(new MessengerDtoToMessengerEntityConverter());
        registry.addConverter(new MessengerEntityToMessengerDtoConverter());
    }

    @Bean
    DispatcherServlet dispatcherServlet () {
        DispatcherServlet ds = new DispatcherServlet();
        ds.setThrowExceptionIfNoHandlerFound(true);
        return ds;
    }

}
