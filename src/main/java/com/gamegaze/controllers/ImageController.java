package com.gamegaze.controllers;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gamegaze.domain.Image;
import com.gamegaze.service.ImageService;

@Controller
public class ImageController {
    
    @Autowired
    private ImageService imageService;
    
    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        imageService.saveImage(image);
        return "redirect:/images";
    }
    
    @GetMapping("/images")
    public String showImages(Model model) {
        List<Image> images = imageService.getImages();
        model.addAttribute("images", images);
        return "images";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Image image = imageService.getImage(id);
        byte[] imageData = image.getData();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
    }
    
}