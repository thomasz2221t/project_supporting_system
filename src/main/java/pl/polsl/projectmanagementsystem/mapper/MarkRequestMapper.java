package pl.polsl.projectmanagementsystem.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.management.api.model.MarkRequestModelApi;
import pl.polsl.projectmanagementsystem.dto.MarkRequestDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MarkRequestMapper {

    MarkRequestDto mapModelApiToDto(MarkRequestModelApi markRequestModelApi);
}
