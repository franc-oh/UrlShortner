package com.lombre.urlShortner.url.service;

import com.lombre.urlShortner.url.dto.UrlDTO;
import com.lombre.urlShortner.url.dto.UrlResDTO;
import com.lombre.urlShortner.url.entity.Url;
import com.lombre.urlShortner.url.repository.UrlRepository;
import com.lombre.urlShortner.util.EncodeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService{

    private final UrlRepository urlRepository;

    @Value("${prop.service.url.domain}")
    private String SERVICE_URL_DOMAIN;

    /**
     * url 압축
     * @param origUrl
     * @return UrlResDTO (resultCode, resultMsg, [UrlDTO])
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UrlResDTO convertUrl(String origUrl) {

        // 1. url 유효성 체크
        boolean urlCheck = new UrlValidator().isValid(origUrl);

        if (!urlCheck) {
            return new UrlResDTO("1000", "올바른 URL이 아닙니다.", new UrlDTO());
        }

        // 2. url 등록여부 확인 후 처리
        UrlDTO resultData;
        Url entity;
        if(urlRepository.existsByOrigUrl(origUrl)) {
            // 2-1. 이미 등록 된 URL ==> 카운트 증감
            entity = urlRepository.findByOrigUrl(origUrl);
            entity.updateConvertCnt(entity.getConvertCnt()+1);

        } else {
            // 2-2. 신규 URL일 경우 ==> 신규등록
            entity = urlRepository.save(Url.builder()
                                            .origUrl(origUrl)
                                            .convertCnt(1L)
                                            .build());

            String convertUrl = "";
            try {
                convertUrl = EncodeUtils.base62Encoding(entity.getId()); // Id -> 암호화(단축 URL로 사용)

                // 암호화된 ID가 8글자 이상 일 경우 => 실패처리
                if(8 < convertUrl.length())
                    throw new IllegalStateException();

            } catch (Exception e) {
                e.printStackTrace();
                return new UrlResDTO("2000", "URL 암호화에 실패했습니다.", new UrlDTO());
            }

            entity.updateConvertUrl(SERVICE_URL_DOMAIN +  convertUrl);

        }

        String resultMsg = "URL 변환이 완료됐습니다.\n" + "(해당 URL " +entity.getConvertCnt() + "번째 압축요청)";
        resultData = new UrlDTO(entity);
        return new UrlResDTO("0000", resultMsg, resultData);

    }

    /**
     * 압축 Url로 요청 시 원래 URL로 리다이렉트
     * @param encodeStr
     * @return resultUrl
     * @throws Exception
     */
    @Override
    @Transactional
    public String redirectOrigUrl(String encodeStr) throws Exception {
        String convertUrl = SERVICE_URL_DOMAIN + encodeStr;

        // 1. URL 등록여부 확인 후 처리
        String resultUrl = "";
        if(urlRepository.existsByConvertUrl(convertUrl)) {
            // 1-1. URL정보가 존재한다면, OrigUrl 리턴
            resultUrl = urlRepository.findByConvertUrl(convertUrl).getOrigUrl();

        } else {
            // 1-2. 존재하지 않는다면, 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return resultUrl;
    }

}
