package com.kuwai.ysy.module.circle.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.home.bean.HomeBean;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.ArrayList;
import java.util.List;


public class CircleAdapter extends BaseQuickAdapter<HomeBean.IssueListBean.ItemListBean, BaseViewHolder> {

    private String[] imgList = new String[]{"http://pic.chinahpsy.com/home/750/gl.jpg", "http://pic.chinahpsy.com/home/750/cq.jpg", "http://pic.chinahpsy.com/home/750/yddm.jpg"};
    private List<ImageView> mViewList = new ArrayList<>();

    public CircleAdapter(List data) {
        super(R.layout.item_team_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBean.IssueListBean.ItemListBean item) {
        GlideUtil.load(mContext, item.getData().getImage(), ((ImageView) helper.getView(R.id.iv_item_team_cover)));
        helper.addOnClickListener(R.id.fbl_item_team);
        mViewList.add(new ImageView(mContext));
        mViewList.add((ImageView) helper.getView(R.id.iv_item_team_cover));
        for (int i = 2; i < imgList.length; i++) {
            mViewList.add(new ImageView(mContext));
        }
        ((ImageView) helper.getView(R.id.iv_item_team_cover)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ImageInfo imageInfo;
                final List<ImageInfo> imageInfoList = new ArrayList<>();
                for (int i = 0; i < imgList.length; i++) {
                    imageInfo = new ImageInfo();
                    imageInfo.setOriginUrl(imgList[i]);// 原图
                    imageInfo.setThumbnailUrl(imgList[i]);// 缩略图，实际使用中，根据需求传入缩略图路径。如果没有缩略图url，可以将两项设置为一样，并隐藏查看原图按钮即可。
                    imageInfoList.add(imageInfo);
                    imageInfo = null;
                }

                ImagePreview
                        .getInstance()
                        .setContext(mContext)
                        .setIndex(1)// 默认显示第几个
                        .setImageInfoList(imageInfoList)// 图片集合
                        .setShowDownButton(true)// 是否显示下载按钮
                        .setShowOriginButton(true)// 是否显示查看原图按钮
                        .setFolderName("BigImageViewDownload")// 设置下载到的文件夹名（保存到根目录）
                        .setScaleLevel(1, 3, 8)// 设置三级放大倍数，分别是最小、中等、最大倍数。
                        .setZoomTransitionDuration(500)// 设置缩放的动画时长
                        .start();// 开始跳转*/
            }
        });
    }

}
