package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.IOException;

@Service
public class AvatarService {

    private static final Logger logger = LoggerFactory.getLogger(AvatarService.class);
    private final AvatarRepository avatarRepository;

    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatar) throws IOException {
        logger.info("Was invoked method to upload avatar for student with id {}", studentId);
    }

    public Avatar findAvatar(Long id) {
        logger.info("Was invoked method to find avatar with id {}", id);
        return avatarRepository.findById(id).orElseThrow(() -> {
            logger.error("There is no avatar with id = {}", id);
            return new RuntimeException("Avatar not found");
        });
    }

    public Page<Avatar> getAvatars(Pageable pageable) {
        logger.info("Was invoked method to get avatars with pagination");
        return avatarRepository.findAll(pageable);
    }
}

