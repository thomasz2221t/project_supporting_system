package pl.polsl.projectmanagementsystem.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.management.api.model.FileResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.FileResponseDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FileResponseMapper {

    FileResponseModelApi mapDtoToModelApi(FileResponseDto fileResponseDto);
}
