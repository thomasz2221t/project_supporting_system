package pl.polsl.projectmanagementsystem.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polsl.management.api.model.StudentGroupModelApi;
import pl.polsl.management.api.model.StudentResponseModelApi;
import pl.polsl.projectmanagementsystem.dto.StudentGroupDto;
import pl.polsl.projectmanagementsystem.mapper.student.StudentMapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = StudentMapper.class)
public interface StudentGroupMapper {

    @Mapping(source = "student.albumNo", target = "id")
    @Mapping(source = "student.firstName", target = "firstName")
    @Mapping(source = "student.lastName", target = "lastName")
    @Mapping(source = "student.birthDate", target = "birthDate")
    @Mapping(source = "student.isActive", target = "isActive")
    StudentResponseModelApi mapDtoToModelApi(StudentGroupDto studentGroupDto);

    @Mapping(source = "student.albumNo", target = "id")
    @Mapping(source = "student.firstName", target = "firstName")
    @Mapping(source = "student.lastName", target = "lastName")
    @Mapping(source = "student.birthDate", target = "birthDate")
    @Mapping(source = "mark", target = "mark")
    StudentGroupModelApi mapDtoToCompleteModelApi(StudentGroupDto studentGroupDto);
}
