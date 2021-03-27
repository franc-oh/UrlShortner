package com.lombre.urlShortner.url.dto;

import com.lombre.urlShortner.url.entity.Url;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UrlDTO {
    private Long id;
    private String origUrl;
    private String convertUrl;
    private Long convertCnt;


    public UrlDTO(Url url) {
        this.id = url.getId();
        this.origUrl = url.getOrigUrl();
        this.convertUrl = url.getConvertUrl();
        this.convertCnt = url.getConvertCnt();
    }
}
