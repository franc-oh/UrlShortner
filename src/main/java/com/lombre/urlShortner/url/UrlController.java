package com.lombre.urlShortner.url;

import com.lombre.urlShortner.url.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    /**
     * 단축 URL 접속 시 원래 URL로 리다이렉트
     * @return
     */
    @GetMapping("/url/{encodeStr}")
    public RedirectView redirectOrigUrl(@PathVariable String encodeStr) throws Exception{
        return new RedirectView(urlService.redirectOrigUrl(encodeStr));
    }

}
