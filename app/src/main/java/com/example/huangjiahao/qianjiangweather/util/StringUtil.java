package com.example.huangjiahao.qianjiangweather.util;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by xiaoshu on 2016/12/24.
 * 字符串工具类
 */
public class StringUtil {
    /**
     * 创建｛文字内容、字体颜色、字体大小｝分段文字集合体
     *
     * @param text
     * @param color
     * @param textSize
     * @return
     */
    public static SpannableStringBuilder creSpanString(String[] text,
                                                       int[] color, int[] textSize) {
        if (text == null || color == null || textSize == null)
            throw new IllegalArgumentException("参数不能为空");
        if (text.length != color.length || text.length != textSize.length)
            throw new IllegalArgumentException("参数数组长度不一致");
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            for (int i = 0; i < text.length; i++) {
                SpannableString sp = new SpannableString(text[i]);
                sp.setSpan(new ForegroundColorSpan(color[i]), 0, sp.length(),
                        Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                sp.setSpan(new AbsoluteSizeSpan(textSize[i], true), 0,
                        sp.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(sp);
            }
            return sb;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb;
    }

    public static String getLastPathSegment(String content) {
        if(content == null || content.length() == 0){
            return "";
        }
        String[] segments = content.split("/");
        if(segments.length > 0) {
            return segments[segments.length - 1];
        }
        return "";
    }

    /**
     * 中文字符编码转换成utf-8编码格式
     * @param content
     * @return
     */
    public static String stringEncode(String content){
        String result="";
        if(TextUtils.isEmpty(content)){
            return result;
        }else {
            try {
                result = URLEncoder.encode(content, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
