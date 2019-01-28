package com.kuwai.ysy.others;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.widget.NineGridView;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


/**
 * @author KCrason
 * @date 2018/4/27
 */
public class NineImageAdapter implements NineGridView.NineGridAdapter<String> {

    private List<String> mImageBeans;

    private Context mContext;

    public NineImageAdapter(Context context, List<String> imageBeans) {
        this.mContext = context;
        this.mImageBeans = imageBeans;
        //int itemSize = (Utils.getScreenWidth() - 2 * Utils.dp2px(4) - Utils.dp2px(54)) / 3;
    }

    @Override
    public int getCount() {
        return mImageBeans == null ? 0 : mImageBeans.size();
    }

    @Override
    public String getItem(int position) {
        return mImageBeans == null ? null :
                position < mImageBeans.size() ? mImageBeans.get(position) : null;
    }

    @Override
    public View getView(int position, View itemView) {
        ImageView imageView;
        if (itemView == null) {
            imageView = new ImageView(mContext);
            imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey_bd));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            imageView = (ImageView) itemView;
        }
        String url = mImageBeans.get(position);
       // GlideUtil.load(mContext, url, imageView);
        GlideUtil.loadRetangle(mContext,url,imageView);
        return imageView;
    }
}
