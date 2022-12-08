package wpl.server.file_storage;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api/v0/file")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileController {

    private final FileService fileService;

//    @PostMapping("/upload")
//    @Transactional
//    public ResponseEntity<Message> upload(@RequestParam(value = "file") MultipartFile file) {
//        return ResponseEntity.ok(fileService.upload(file, "test"));
//    }
//
//    @PostMapping("/upload-multiple")
//    @Transactional
//    public ResponseEntity<List<Message>> uploadMultiple(@RequestParam(value = "files") MultipartFile[] files) {
//        return ResponseEntity.ok(Arrays.stream(files)
//                .map(f -> upload(f).getBody())
//                .collect(Collectors.toList()));
//    }

    @GetMapping("")
    public ResponseEntity<Resource> download(HttpServletRequest request, @RequestParam String uri) {
        Resource resource = fileService.loadAsResource(uri);
        String contentType = "application/octet-stream";
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
