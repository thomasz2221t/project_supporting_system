package pl.polsl.projectmanagementsystem.mapper.lecturer;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.management.api.model.LecturerModelApi;
import pl.polsl.management.api.model.LecturerResponseModelApi;
import pl.polsl.management.api.model.LecturerUpdateModelApi;
import pl.polsl.management.api.model.UserResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.LecturerDto;
import pl.polsl.projectmanagementsystem.dto.UserDto;
import pl.polsl.projectmanagementsystem.model.Lecturer;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LecturerMapper {

    LecturerDto mapModelApiToDto(LecturerModelApi lecturerModelApi);
    LecturerDto mapEntityToDto(Lecturer lecturer);
    Lecturer mapDtoToEntity(LecturerDto lecturerDto);
    LecturerResponseModelApi mapDtoToModelApi(LecturerDto lecturerDto);
    LecturerDto mapModelApiToDto(LecturerUpdateModelApi lecturerModelApi);


    default Lecturer mapDtoToNewEntity(LecturerDto lecturerDto) {
        Lecturer lecturer = mapDtoToEntity(lecturerDto);

        lecturer.setIsActive(true);

        return lecturer;
    }

}
