package com.lombre.urlShortner.url.service;

import com.lombre.urlShortner.url.dto.UrlResDTO;
import com.lombre.urlShortner.url.entity.Url;
import com.lombre.urlShortner.url.repository.UrlRepository;
import com.lombre.urlShortner.util.EncodeUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UrlServiceTest {

    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlRepository urlRepository;

    @Value("${prop.service.url.domain}")
    private String SERVICE_URL_DOMAIN;


    @After
    public void tearDown() {
        urlRepository.deleteAll();
    }

    /**
     * URL 압축 테스트_신규
     * @throws Exception
     */
    @Test
    public void test1_convertUrl_newUrl_success() throws Exception{
        System.out.println("=========================== 1");
        String origUrl = "https://en.wikipedia.org/wiki/URL_shortening";
        String chkResultCode = "0000";
        String chkConvertUrl = SERVICE_URL_DOMAIN + EncodeUtils.base62Encoding(1000000001L);
        Long chkConvertCnt = 1L;

        UrlResDTO testDTO = urlService.convertUrl(origUrl);

        Assert.assertEquals(testDTO.getResultCode(), chkResultCode);
        Assert.assertEquals(testDTO.getResultData().getOrigUrl(), origUrl);
        Assert.assertEquals(testDTO.getResultData().getConvertUrl(), chkConvertUrl);
        Assert.assertEquals(testDTO.getResultData().getConvertCnt(), chkConvertCnt);

    }

    /**
     * URL 압축 테스트_기존
     * @throws Exception
     */
    @Test
    public void test2_convertUrl_existUrl_success() throws Exception{
        System.out.println("=========================== 2");
        String origUrl = "https://en.wikipedia.org/wiki/URL_shortening";
        String convertUrl = SERVICE_URL_DOMAIN + EncodeUtils.base62Encoding(1000000001L);
        String chkResultCode = "0000";
        Long chkConvertCnt = 2L;

        urlRepository.save(Url.builder()
                        .origUrl(origUrl)
                        .convertUrl(convertUrl)
                        .convertCnt(1L).build());

        UrlResDTO testDTO = urlService.convertUrl(origUrl);

        Assert.assertEquals(testDTO.getResultCode(), chkResultCode);
        Assert.assertEquals(testDTO.getResultData().getOrigUrl(), origUrl);
        Assert.assertEquals(testDTO.getResultData().getConvertUrl(), convertUrl);
        Assert.assertEquals(testDTO.getResultData().getConvertCnt(), chkConvertCnt);

    }


    /**
     * URL 압축 테스트_실패 (Url 형식에 안맞았을 때 리턴 값 체크)
     */
    @Test
    public void test3_convertUrl_fail_urlValidate() {
        String origUrl = "https://en.wikiped";
        String chkResultCode = "1000";
        String chkResultMsg = "올바른 URL이 아닙니다.";

        UrlResDTO testDTO = urlService.convertUrl(origUrl);

        Assert.assertEquals(testDTO.getResultCode(), chkResultCode);
        Assert.assertEquals(testDTO.getResultMsg(), chkResultMsg);

    }


    /**
     * 압축 URL => 원래 URL 테스트
     * @throws Exception
     */
    @Test
    public void test4_redirectOrigUrl_success() throws Exception {
        String encodeStr = EncodeUtils.base62Encoding(1000000001L);
        String chkOrigUrl = "https://en.wikipedia.org/wiki/URL_shortening";

        // 체크 전 등록처리
        urlRepository.save(Url.builder()
                        .origUrl("https://en.wikipedia.org/wiki/URL_shortening")
                        .convertUrl(SERVICE_URL_DOMAIN + EncodeUtils.base62Encoding(1000000001L))
                        .convertCnt(1L).build());

        String testOrigUrl = urlService.redirectOrigUrl(encodeStr);
        Assert.assertEquals(testOrigUrl, chkOrigUrl);

    }

    /**
     * 압축 URL => 원래 URL 테스트_실패 (압축 URL이 존재하지 않는 데이터인 경우 404 에러로 던지는지 체크)
     * @throws Exception
     */
    @Test
    public void test5_redirectOrigUrl_fail_notExistsUrl() {
        String notExistStr = "AbS=2";
        boolean chkException = false;
        String chkStr = "404";

        String testExceptionMsg = "";
        String testOrigUrl = "";
        try {
            testOrigUrl = urlService.redirectOrigUrl(notExistStr);

        } catch (Exception e) {
            chkException = true;
            testExceptionMsg = e.getMessage();
        }

        Assert.assertTrue(chkException);
        Assert.assertTrue( testExceptionMsg.contains(chkStr) );
    }

}