package ru.hogwarts.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.IOException;

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;

    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatar) throws IOException {
        // Логика для загрузки аватара
    }

    public Avatar findAvatar(Long id) {
        return avatarRepository.findById(id).orElse(null);
    }

    public Page<Avatar> getAvatars(Pageable pageable) {
        return avatarRepository.findAll(pageable);
    }
}
