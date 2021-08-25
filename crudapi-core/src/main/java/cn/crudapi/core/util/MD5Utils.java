package cn.crudapi.core.util;

import org.springframework.util.DigestUtils;

public class MD5Utils {
    //盐，用于混交md5
    private static String salt = "AirSpider_123456";

    /**
     * 生成md5
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        String base = str + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}