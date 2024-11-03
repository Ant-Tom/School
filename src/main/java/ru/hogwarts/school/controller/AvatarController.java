package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/avatars")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{studentId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatar) {
        try {
            avatarService.uploadAvatar(studentId, avatar);
            return ResponseEntity.ok("Avatar uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload avatar: " + e.getMessage());
        }
    }

    @GetMapping(value = "/{id}/preview")
    public ResponseEntity<byte[]> downloadAvatarPreview(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);
        if (avatar == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.ok().headers(headers).body(avatar.getData());
    }

    @GetMapping(value = "/{id}/download")
    public void downloadAvatarFile(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);
        if (avatar == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }

        Path path = Path.of(avatar.getFilePath());
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(avatar.getMediaType());
        response.setContentLengthLong(avatar.getFileSize());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            is.transferTo(os);
        } catch (IOException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
