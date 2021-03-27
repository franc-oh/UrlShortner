package com.lombre.urlShortner.url;

import com.lombre.urlShortner.url.dto.UrlResDTO;
import com.lombre.urlShortner.url.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UrlRestController {

    private final UrlService urlService;

    /**
     * URL 단축
     * @param origUrl
     * @return
     */
    @GetMapping("/url/api/convert")
    public UrlResDTO convertUrl(@RequestParam String origUrl) {
        return urlService.convertUrl(origUrl);
    }



}
