package com.gamegaze.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gamegaze.domain.Image;
import com.gamegaze.repository.ImageRepository;

@Service
public class ImageService {
    
    @Autowired
    private ImageRepository imageRepository;
    
    public void saveImage(MultipartFile image) throws IOException {
        Image imageEntity = new Image();
        imageEntity.setData(image.getBytes());
        imageRepository.save(imageEntity);
    }
    
    public List<Image> getImages() {
        return imageRepository.findAll();
    }
    
    public Image getImage(Long id) {
        return imageRepository.findById(id).orElse(null);
    }
}