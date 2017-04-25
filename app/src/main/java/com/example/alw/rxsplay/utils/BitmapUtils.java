package com.example.alw.rxsplay.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by David on 2017/4/25.
 * Des : 图形工具
 */

public class BitmapUtils {
    /**
     * author : David
     * date : 2017/4/25 15:56
     * des : 根据图片的url路径获得Bitmap对象
     */
    public static Bitmap returnBitmap(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;
        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            Log.e("playrxs", "returnBitmap: 获取图片文件失败");
            e.printStackTrace();
        }
        return bitmap;
    }


}
