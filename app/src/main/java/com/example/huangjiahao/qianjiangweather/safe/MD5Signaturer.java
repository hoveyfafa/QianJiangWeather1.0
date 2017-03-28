package com.example.huangjiahao.qianjiangweather.safe;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;


/**
 * MD5签名器
 * 
 * @author guangdong.li
 * @version $Id: MD5Signaturer.java, v 0.1 17 Feb 2016 15:15:34 guangdong.li Exp $
 */
public class MD5Signaturer implements Signaturer {


    private byte[]              key;

    /*
     * public MD5Signaturer(byte[] key) { this.key = key; }
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.bench.platform.signature.Signaturer#check(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    public boolean check(String content, String signature, String charset) {
        // TODO Auto-generated method stub
        return StringUtils.equals(signature, Md5Encrypt.md5(key, content, charset));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.bench.platform.signature.Signaturer#check(java.lang.String,
     * java.lang.String)
     */
    public boolean check(String content, String signature) {
        // TODO Auto-generated method stub
        return check(content, signature, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.bench.platform.signature.Signaturer#check(byte[],
     * java.lang.String)
     */
    public boolean check(byte[] content, String signature) {
        // TODO Auto-generated method stub
        return StringUtils.equals(signature, Md5Encrypt.md5(key, content));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.bench.platform.signature.Signaturer#check(java.io.InputStream,
     * java.lang.String)
     */
    public boolean check(InputStream is, String signature) {
        // TODO Auto-generated method stub
        try {
            return StringUtils.equals(signature, Md5Encrypt.md5(key, IOUtils.toByteArray(is)));
        } catch (IOException e) {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.bench.platform.signature.Signaturer#sign(java.lang.String)
     */
    public String sign(String content) {
        // TODO Auto-generated method stub
        return Md5Encrypt.md5(key, content);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.bench.platform.signature.Signaturer#sign(java.lang.String,
     * java.lang.String)
     */
    public String sign(String content, String charset) {
        // TODO Auto-generated method stub
        return Md5Encrypt.md5(key, content, charset);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.bench.platform.signature.Signaturer#sign(byte[])
     */
    public String sign(byte[] content) {
        // TODO Auto-generated method stub
        return Md5Encrypt.md5(key, content);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.bench.platform.signature.Signaturer#sign(java.io.InputStream)
     */
    public String sign(InputStream is) {
        // TODO Auto-generated method stub
        try {
            return Md5Encrypt.md5(key, IOUtils.toByteArray(is));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String sign(byte[] key, String content) {
        // TODO Auto-generated method stub
        return Md5Encrypt.md5(key, content);
    }

    /**
     * @return Returns the key.
     */
    public byte[] getKey() {
        return key;
    }

    /**
     * @param key
     *            The key to set.
     */
    public void setKey(byte[] key) {
        this.key = key;
    }

    public static void main(String[] args) {

        MD5Signaturer ss = new MD5Signaturer();
        System.out.println(new Date().getTime());
        System.out.println(ss.sign(new Date().getTime() + ""));
        ;

    }

}
