package wpl.server.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UploadQuizRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotNull
    private Integer answer;

    @NotEmpty
    private List<MultipartFile> images;
}
