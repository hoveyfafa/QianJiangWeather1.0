package com.kevin.loopview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kevin.loopview.internal.BaseLoopAdapter;
import com.kevin.loopview.internal.LoopData;
import com.kevin.loopview.internal.loopimage.LoopImageView;

/**
 * 版权所有：XXX有限公司
 *
 * AdLoopAdapter
 *
 * @author zhou.wenkai ,Created on 2015-5-19 22:28:29
 * Major Function：<b>一般数据适配器</b>
 *
 * 注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public class AdLoopAdapter extends BaseLoopAdapter {

    public AdLoopAdapter(Context context, LoopData loopData,
                         ViewPager viewPager) {
        super(context, loopData, viewPager);
    }

    /**
     * 控件操作
     * @param imageUrl
     */
    public View instantiateItemView(String imageUrl, int position) {
        LoopImageView mImageView = new LoopImageView(mContext);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        if(!TextUtils.isEmpty(imageUrl)) {
            mImageView.setImageUrl(imageUrl, defaultImgId, defaultImgId);
        }else{
            mImageView.setImageResource(R.drawable.icon_banner_shouye);
        }
        return mImageView;
    }

}