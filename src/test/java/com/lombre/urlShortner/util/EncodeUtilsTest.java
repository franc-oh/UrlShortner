package com.lombre.urlShortner.util;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EncodeUtilsTest {

    /**
     * URL 암호화 모듈 테스트
     * @throws Exception
     */
    @Test
    public void test1_base62Encoding_test() throws Exception {
        long id = 1000000001;
        String chkStr = "Rq3pFB";

        Assert.assertEquals(EncodeUtils.base62Encoding(id), chkStr);
        Assert.assertEquals(EncodeUtils.base62Encoding(0), "");

    }

}