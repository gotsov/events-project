package com.events.project.util;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public EventMapper personMapper() {
        return Mappers.getMapper(EventMapper.class);
    }
}
