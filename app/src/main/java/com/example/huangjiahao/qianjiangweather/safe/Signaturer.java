package com.example.huangjiahao.qianjiangweather.safe;

import java.io.InputStream;

/**
 * 签名服务类
 * 
 * @author guangdong.li
 * @version $Id: Signaturer.java, v 0.1 17 Feb 2016 15:13:05 guangdong.li Exp $
 */
public interface Signaturer {

    /**
     * 对指定字符串进行签名。
     * 
     * @param key 
     *            指定签名key
     * @param content
     *            要签名的字符串
     * @param keyPairName
     *            key pair
     * 
     * @return base64编码的签名
     */
    String sign(byte[] key, String content);

    /**
     * 对指定字符串进行签名。
     * 
     * @param content
     *            要签名的字符串
     * @param keyPairName
     *            key pair
     * 
     * @return base64编码的签名
     */
    String sign(String content);

    /**
     * 对指定字符串进行签名。
     * 
     * @param content
     *            要签名的字符串
     * @param keyPairName
     *            key pair
     * @param charset
     *            字符串的编码字符集
     * 
     * @return base64编码的签名
     */
    String sign(String content, String charset);

    /**
     * 对指定字节流进行签名。
     * 
     * @param content
     *            要签名的字节流
     * @param keyPairName
     *            key pair
     * 
     * @return base64编码的签名
     */
    String sign(byte[] content);

    /**
     * 对指定输入流进行签名。
     * 
     * @param content
     *            要签名的输入流
     * @param keyPairName
     *            key pair
     * 
     * @return base64编码的签名
     */
    String sign(InputStream content);

    /**
     * 检验content的签名。
     * 
     * @param content
     *            要检验的内容
     * @param signature
     *            签名
     * @param keyPairName
     *            key pair
     * 
     * @return 如果签名正确，则返回<code>true</code>
     */
    boolean check(String content, String signature, String charset);

    /**
     * 检查签名
     * 
     * @param content
     * @param signature
     * @return
     */
    boolean check(String content, String signature);

    /**
     * 检验content的签名。
     * 
     * @param content
     *            要检验的内容
     * @param signature
     *            签名
     * @param keyPairName
     *            key pair
     * 
     * @return 如果签名正确，则返回<code>true</code>
     */
    boolean check(byte[] content, String signature);

    /**
     * 检验content的签名。
     * 
     * @param content
     *            要检验的内容
     * @param signature
     *            签名
     * @param keyPairName
     *            key pair
     * 
     * @return 如果签名正确，则返回<code>true</code>
     */
    boolean check(InputStream content, String signature);
}
