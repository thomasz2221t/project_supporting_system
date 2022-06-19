package pl.polsl.projectmanagementsystem.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportDto {

    private String originalBucket;
    private String originalFileName;
    private String bucket;
    private String key;
}