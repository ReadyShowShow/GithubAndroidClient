package com.jian.github.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ImageLoader {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    private ImageLoader() {
    }

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    public static void loadAsCircle(ImageView target, @DrawableRes int resId) {
        Glide.with(sContext).load(resId).bitmapTransform(new CropCircleTransformation(sContext)).into(target);
    }

    public static void loadAsCircle(ImageView target, String url) {
        Glide.with(sContext).load(url).bitmapTransform(new CropCircleTransformation(sContext)).into(target);
    }

    public static void loadAsCircle(ImageView target, @DrawableRes int defId, String url) {
        Glide.with(sContext)
                .load(url)
                .placeholder(defId)
                .bitmapTransform(new CropCircleTransformation(sContext))
                .into(target);
    }

    public static void load(ImageView target, String url) {
        Glide.with(sContext).load(url).into(target);
    }
}
