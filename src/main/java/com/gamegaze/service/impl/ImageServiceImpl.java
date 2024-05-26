package com.gamegaze.service.impl;

import com.gamegaze.domain.Image;
import com.gamegaze.repository.ImageRepository;
import com.gamegaze.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image saveImage(MultipartFile image) throws IOException {
        Image imageEntity = new Image();
        imageEntity.setData(image.getBytes());
        imageRepository.save(imageEntity);
        return imageEntity;
    }

    @Override
    public List<Image> getImages() {
        return imageRepository.findAll();
    }

    @Override
    public Image getImage(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    @Override
    public Image getDefaultProfileImage() {
        Image defaultProfileImage = new Image();
        try {
            Path defaultImagePath = ResourceUtils.getFile("classpath:static/assets/loginPP.png").toPath();
            byte[] imageData = Files.readAllBytes(defaultImagePath);
            defaultProfileImage.setData(imageData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defaultProfileImage;
    }

    @Override
    public Image getDefaultProfileBanner() {
        Image defaultProfileImage = new Image();
        try {
            Path defaultImagePath = ResourceUtils.getFile("classpath:static/assets/defaultBanner.png").toPath();
            byte[] imageData = Files.readAllBytes(defaultImagePath);
            defaultProfileImage.setData(imageData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defaultProfileImage;
    }
}
