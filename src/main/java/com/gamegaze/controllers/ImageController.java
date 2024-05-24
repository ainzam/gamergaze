package com.gamegaze.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gamegaze.domain.Image;
import com.gamegaze.service.ImageService;

@Controller
public class ImageController {
    
    @Autowired
    private ImageService imageService;
    
    
    @GetMapping("/images")
    public String showImages(Model model) {
        List<Image> images = imageService.getImages();
        model.addAttribute("images", images);
        return "images";
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Image image = imageService.getImage(id);
        if (image!= null) {
            byte[] imageData = image.getData();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}