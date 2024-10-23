package com.dclib.glide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * Glide常用工具类
 * Created on 2018/5/10..
 *
 * @author daichao
 */
public class GlideUtil {

    /**
     * 加载图片
     *
     * @param context   上下文
     * @param url       图片地址
     * @param imageView view
     */
    public static void loadImage(Context context, int url, ImageView imageView) {
        loadImage(context, url, imageView, R.drawable.shape_default);
    }

    /**
     * 加载图片
     *
     * @param context   上下文
     * @param url       图片地址
     * @param imageView view
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        loadImage(context, url, imageView, R.drawable.shape_default);
    }

    /**
     * 加载图片
     *
     * @param context      上下文
     * @param url          图片地址
     * @param imageView    view
     * @param defaultResId 默认图片id
     */
    public static void loadImage(Context context, int url, ImageView imageView, Integer defaultResId) {
        if (context == null) {
            return;
        }

        RequestOptions options = new RequestOptions()
                .placeholder(defaultResId)
                .error(defaultResId);

        Glide.with(context)
                .load(url)
                .apply(options)
//                .dontAnimate()
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param context      上下文
     * @param url          图片地址
     * @param imageView    view
     * @param defaultResId 默认图片id
     */
    public static void loadImage(Context context, String url, ImageView imageView, Integer defaultResId) {
        if (context == null) {
            return;
        }

        RequestOptions options = new RequestOptions()
                .placeholder(defaultResId)
                .error(defaultResId);

        Glide.with(context)
                .load(url)
                .apply(options)
                .dontAnimate()
                .into(imageView);
    }


    /**
     * 加载圆形图片
     *
     * @param context   上下文
     * @param url       图片地址
     * @param imageView view
     */
    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        loadCircleImage(context, url, imageView, R.drawable.shape_default);
    }

    /**
     * 加载圆形图片
     *
     * @param context      上下文
     * @param url          图片地址
     * @param imageView    view
     * @param defaultResId 默认图片id
     */
    public static void loadCircleImage(final Context context, String url, final ImageView imageView, Integer defaultResId) {
        if (context == null) {
            return;
        }

        if (TextUtils.isEmpty(url)) {
            Glide.with(context)
                    .asBitmap()
                    .load(defaultResId)
                    .dontAnimate()
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageView.setImageDrawable(circularBitmapDrawable);
                        }

                    });
        } else {
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .placeholder(defaultResId)
                    .dontAnimate()
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageView.setImageDrawable(circularBitmapDrawable);
                        }

                    });
        }
    }


    /**
     * 加载圆角图片
     *
     * @param context      上下文
     * @param url          图片地址
     * @param imageView    view
     * @param radius       圆角
     * @param defaultResId 默认图片id
     */
    public static void loadRadiusImage(final Context context, String url, final ImageView imageView, final int radius, Integer defaultResId) {
        if (context == null) {
            return;
        }

        if (TextUtils.isEmpty(url)) {
            Glide.with(context)
                    .load(defaultResId)
                    .transform(new CenterCrop(), new RoundedCorners(dp2px(radius)))
                    .into(imageView);
        } else {
            Glide.with(context)
                    .load(url)
                    .placeholder(defaultResId)
                    .transform(new CenterCrop(), new RoundedCorners(dp2px(radius)))
                    .into(imageView);
        }
    }

    /**
     * 加载圆角图片
     *
     * @param context      上下文
     * @param url          图片地址
     * @param imageView    view
     * @param defaultResId 默认图片id
     */
    public static void loadRadiusLeftTopImage(final Context context, String url, final ImageView imageView, final int leftTop, final int rightTop, final int leftBottom, final int rightBottom, Integer defaultResId) {
        if (context == null) {
            return;
        }

        if (TextUtils.isEmpty(url)) {
            Glide.with(context)
                    .load(defaultResId)
                    .transform(new RoundedCornersTransform(dp2px(leftTop), dp2px(rightTop), dp2px(leftBottom), dp2px(rightBottom)))
                    .into(imageView);
        } else {
            Glide.with(context)
                    .load(url)
                    .placeholder(defaultResId)
                    .transform(new RoundedCornersTransform(dp2px(leftTop), dp2px(rightTop), dp2px(leftBottom), dp2px(rightBottom)))
                    .into(imageView);
        }
    }

    /**
     * 加载背景图片
     *
     * @param context      上下文
     * @param url          图片地址
     * @param view         view
     * @param defaultResId 默认图片id
     */
    public static void loadBgImage(final Context context, String url, final View view, Integer defaultResId) {
        if (context == null) {
            return;
        }

        RequestOptions options = new RequestOptions()
                .placeholder(defaultResId)
                .error(defaultResId)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(options)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        RoundedBitmapDrawable radiusBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        view.setBackground(radiusBitmapDrawable);
                    }
                });
    }

    private static int dp2px(float dpValue) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }
}
