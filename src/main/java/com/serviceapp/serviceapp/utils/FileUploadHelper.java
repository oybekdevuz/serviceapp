package com.serviceapp.serviceapp.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileUploadHelper {

    @Value("C:\\Users\\Acer\\Desktop\\serviceapp\\uploads")
    private String uploadDir;

    public String uploadFile(MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            throw new Exception("Fayl yuklanmagan");
        }

        // Fayl nomini o'zgartirish
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Faylni joylashgan papkani yo'lini aniqlash
        Path uploadPath = Paths.get(uploadDir);

        // Agar papka mavjud bo'lmasa, uni yaratish
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Faylni joylashgan papkaga yuklash
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        // Faylning to'liq yo'lini qaytarish
        return filePath.toString();
    }
}
