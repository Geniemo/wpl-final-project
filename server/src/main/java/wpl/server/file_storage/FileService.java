package wpl.server.file_storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wpl.server.AppProperties;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    private final Path root;
    private final FileRepository fileRepository;

    @Autowired
    public FileService(AppProperties appProperties, FileRepository fileRepository) {
        root = Paths.get(appProperties.getFileProperties().getRoot()).toAbsolutePath().normalize();
        this.fileRepository = fileRepository;
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new FileException("cannot create root directories: " + root, e);
        }
    }

    @Transactional
    public File upload(MultipartFile file, String path) {
        try {
            if (file.isEmpty()) {
                throw new FileException("cannot save empty file");
            }
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            if (filename.contains("..")) {
                throw new FileException("filename is not valid: " + filename);
            }
            
            Path targetLocation = root.resolve(path);
            Files.createDirectories(targetLocation);
            Files.copy(file.getInputStream(), targetLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);

            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v0/file")
                    .queryParam("uri", path + "/" + filename)
                    .toUriString();
            File fileEntity = File.createFile(uri);
            fileRepository.save(fileEntity);
            return fileEntity;
        } catch (IOException e) {
            throw new FileException("fail to save file. try it again.", e);
        }
    }

    public Resource loadAsResource(String uri) {
        try {
            Path path = root.resolve(uri).normalize();
            UrlResource resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("cannot find file at " + uri);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
