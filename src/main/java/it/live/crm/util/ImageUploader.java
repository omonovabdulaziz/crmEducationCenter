package it.live.crm.util;

import it.live.crm.exception.MainException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class ImageUploader {
    private static final String MAIN_UPLOAD_DIRECTORY = "DOCUMENTS";

    public static void imageUploader(String fileName, MultipartFile multipartFile) {
        try {
            Path uploadDirectory = Paths.get(MAIN_UPLOAD_DIRECTORY);
            if (!Files.exists(uploadDirectory)) Files.createDirectories(uploadDirectory);
            Path filePath = uploadDirectory.resolve(fileName);
            multipartFile.transferTo(filePath);
        } catch (Exception e) {
            throw new MainException("Error while image uploading ...");
        }
    }
}