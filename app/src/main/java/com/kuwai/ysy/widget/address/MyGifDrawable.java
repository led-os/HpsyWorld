package com.kuwai.ysy.widget.address;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.gif.GifDrawable;

public class MyGifDrawable extends GifDrawable{

    public MyGifDrawable(Context context, GifDecoder gifDecoder, Transformation<Bitmap> frameTransformation, int targetFrameWidth, int targetFrameHeight, Bitmap firstFrame) {
        super(context, gifDecoder, frameTransformation, targetFrameWidth, targetFrameHeight, firstFrame);
    }

    @Override
    public void stop() {
        super.stop();

        //这里之所以要判断callBack是否为null,是因为在RecycleView中当gif划出屏幕，然后再划入屏幕时，callback会为null,

        //在GifDrawable中的onFrameReady（）方法，会判断如果callback为null，会直接会调用onstop方法,当gif图在recycleview中，划出//屏幕再划入屏幕时，callback会为null,这是就会出现，当第一帧刚准备好显示出来，就会马上调用onstop()方法，为避免这种情况，在此加入//下方这个判断。

        if (getCallback() != null) {
            //GIF播放完成，可以做一些操作
        }
    }
}
