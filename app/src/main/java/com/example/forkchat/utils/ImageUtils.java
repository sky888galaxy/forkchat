package com.example.forkchat.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片工具类
 */
public class ImageUtils {
    
    public static void loadImage(Context context, String url, ImageView imageView) {
        if (url != null && !url.isEmpty()) {
            Glide.with(context)
                    .load(url)
                    .into(imageView);
        }
    }
    
    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        if (url != null && !url.isEmpty()) {
            Glide.with(context)
                    .load(url)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(imageView);
        }
    }
    
    public static String saveImageToLocal(Context context, Uri imageUri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            
            String fileName = "IMG_" + System.currentTimeMillis() + ".jpg";
            File file = new File(context.getFilesDir(), fileName);
            
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos);
            fos.flush();
            fos.close();
            
            if (inputStream != null) {
                inputStream.close();
            }
            
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

