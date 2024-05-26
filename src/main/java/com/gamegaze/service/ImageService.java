package com.gamegaze.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gamegaze.domain.Image;

public interface ImageService {
    Image saveImage(MultipartFile image) throws IOException;
    List<Image> getImages();
    Image getImage(Long id);
    Image getDefaultProfileImage();
    Image getDefaultProfileBanner();
}
