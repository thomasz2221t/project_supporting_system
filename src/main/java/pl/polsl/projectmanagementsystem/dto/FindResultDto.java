package pl.polsl.projectmanagementsystem.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindResultDto<T> {

    private Long startElement;
    private Long totalCount;
    private Long count;
    private List<T> results;
}
