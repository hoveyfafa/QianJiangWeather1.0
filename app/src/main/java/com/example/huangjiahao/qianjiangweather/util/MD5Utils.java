package com.example.huangjiahao.qianjiangweather.util;

import android.text.TextUtils;

import java.security.MessageDigest;

/**
 * Created by JH.H on 2017/4/5.
 */

public class MD5Utils {

    public static String md5(String strMd5) {
        if (TextUtils.isEmpty(strMd5)) {
            return null;
        }
        String sRet = null;
        try {
            MessageDigest alga = MessageDigest
                    .getInstance("MD5");
            alga.update(strMd5.getBytes());
            byte[] digesta = alga.digest();
            sRet = byte2hex(digesta);
        } catch (java.security.NoSuchAlgorithmException ex) {
        }
        return sRet;
    }

    public static String byte2hex(byte[] b)
    {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + "";
        }
        return hs;
    }

    public final static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

}
