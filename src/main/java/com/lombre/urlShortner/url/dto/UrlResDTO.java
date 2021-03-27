package com.lombre.urlShortner.url.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UrlResDTO {

    private String resultCode;  // 응답코드
    private String resultMsg;   // 응답 메세지
    private UrlDTO resultData;  // 응답 데이터 (URL)

    public UrlResDTO(String resultCode, String resultMsg, UrlDTO resultData) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.resultData = resultData;
    }
}
