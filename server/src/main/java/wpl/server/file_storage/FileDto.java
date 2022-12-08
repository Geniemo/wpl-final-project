package wpl.server.file_storage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileDto {

    private String uri;

    public static FileDto createFileDto(File file) {
        return new FileDto(file.getUri());
    }
}
