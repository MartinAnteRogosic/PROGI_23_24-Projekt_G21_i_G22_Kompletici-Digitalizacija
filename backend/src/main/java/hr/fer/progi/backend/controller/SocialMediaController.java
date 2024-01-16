package hr.fer.progi.backend.controller;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookLink;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/social")
public class SocialMediaController {

    @PostMapping("/shareOnFacebook")
    public String shareOnFacebook(@RequestParam("fileUrl") String fileUrl, @RequestParam("caption") String caption) {

        return "https://www.facebook.com/sharer/sharer.php?u=" + fileUrl + "&quote=" + caption;
    }

}
