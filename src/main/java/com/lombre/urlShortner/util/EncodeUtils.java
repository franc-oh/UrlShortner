package com.lombre.urlShortner.util;

/**
 * Encoding & Decoding Utils
 */
public class EncodeUtils {
    static final int BASE62_RADIX = 62;
    static final String BASE62_CODEC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * base62 인코딩 (long -> String)
     * @param param
     * @return sb
     * @throws Exception
     */
    public static String base62Encoding(long param) throws Exception {
        StringBuffer sb = new StringBuffer();
        while(param > 0) {
            sb.append(BASE62_CODEC.charAt((int) (param % BASE62_RADIX)));
            param /= BASE62_RADIX;
        }
        return sb.toString();
    }

}
