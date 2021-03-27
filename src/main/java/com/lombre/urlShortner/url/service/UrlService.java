package com.lombre.urlShortner.url.service;

import com.lombre.urlShortner.url.dto.UrlResDTO;

public interface UrlService {

    UrlResDTO convertUrl(String origUrl);

    String redirectOrigUrl(String encodeStr) throws Exception;
}
