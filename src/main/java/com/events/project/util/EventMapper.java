package com.events.project.util;

import com.events.project.models.dtos.EventDto;
import com.events.project.models.entities.Event;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingTarget;

@Mapper
public interface EventMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EventDto dto, @MappingTarget Event entity);
}
