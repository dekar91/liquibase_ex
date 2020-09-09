package ru.team42.analyzer.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
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
import ru.team42.analyzer.dto.converters.RoleDtoToRoleEntityConverter;
import ru.team42.analyzer.dto.converters.RoleEntityToRoleDtoConverter;
import ru.team42.analyzer.dto.converters.UserDtoToUserEntityConverter;
import ru.team42.analyzer.dto.converters.UserEntityToUserDtoConverter;

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

        registry.addConverter(new UserDtoToUserEntityConverter());
        registry.addConverter(new UserEntityToUserDtoConverter());

        registry.addConverter(new RoleDtoToRoleEntityConverter());
        registry.addConverter(new RoleEntityToRoleDtoConverter());
    }

    @Bean
    DispatcherServlet dispatcherServlet () {
        DispatcherServlet ds = new DispatcherServlet();
        ds.setThrowExceptionIfNoHandlerFound(true);
        return ds;
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return customizer -> customizer
                .featuresToEnable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS)
                .featuresToDisable(
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                        DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE
                )
                .modulesToInstall(
                        new KotlinModule(),
                        new JavaTimeModule()
                )
                .serializationInclusion(JsonInclude.Include.NON_NULL);
    }

}
