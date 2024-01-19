package hr.fer.progi.backend.controller;
import hr.fer.progi.backend.controller.SocialMediaController;
import hr.fer.progi.backend.dto.SocialMediaDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SocialMediaControllerTest {

    private final SocialMediaController socialMediaController = new SocialMediaController();
    private final RestTemplate restTemplate = new RestTemplate();
    private final MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

    @Test
    void testShareOnFacebook() throws Exception {
        String fileUrl = "https:image.jpg";
        String caption = "slika";
        String expectedUrl = "https://www.facebook.com/sharer/sharer.php?u=" + fileUrl + "&quote=" + caption;

        SocialMediaDto socialMediaDto = SocialMediaDto.builder()
                .fileUrl(fileUrl)
                .caption(caption)
                .build();
        mockServer.expect(requestTo("/api/v1/social/shareOnFacebook?fileUrl=" + fileUrl + "&caption=" + caption))
                .andRespond(withSuccess(expectedUrl, MediaType.APPLICATION_JSON));

        String actualResponse = socialMediaController.shareOnFacebook(socialMediaDto);

        mockServer.verify();
        assertEquals(expectedUrl, actualResponse);
    }
}