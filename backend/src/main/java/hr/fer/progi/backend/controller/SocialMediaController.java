package hr.fer.progi.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/social")
public class SocialMediaController {

    @PostMapping("/shareOnFacebook")
    public String shareOnFacebook(@RequestParam("fileUrl") String fileUrl, @RequestParam("caption") String caption) {

        // Constructing the Facebook share link
        // You can return this link to the frontend, and users can click on it to share on Facebook
        return "https://www.facebook.com/sharer/sharer.php?u=" + fileUrl + "&quote=" + caption;
    }
}
