package com.yunwang.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by a on 2016/11/25.
 */

public class ImageUtils {

    /**
     * @Author: Lqiang
     * @CreteDate: 2015年3月26日 上午10:58:26
     * @Title:
     * @Description: 等比压缩图片到指定比例大小
     * @ModifiedBy:
     * @param imagePath
     * @param pixelW
     * @param pixelH
     * @return
     */
    public static Bitmap compressImageBySize(String imagePath, int pixelW, int pixelH) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, newOpts);// 此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w >= h && w > pixelW) {
            // 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / pixelW);
        } else if (w < h && h > pixelH) {
            // 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / pixelH);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(imagePath, newOpts);
        return bitmap;
    }

    /**
     * @Author: Lqiang
     * @CreteDate: 2015年3月26日 上午11:19:32
     * @Title:
     * @Description: 精确压缩到指定大小
     * @ModifiedBy:
     * @param srcBmp
     * @param pixelW
     * @param pixelH
     * @return
     */
    public static Bitmap compressImageBySize(Bitmap srcBmp, int pixelW, int pixelH) {
        Bitmap dstBmp = Bitmap.createScaledBitmap(srcBmp, pixelW, pixelH, false);
        if (srcBmp != dstBmp) {
            srcBmp.recycle();
        }
        return dstBmp;
    }

    /**
     * @Author: Lqiang
     * @CreteDate: 2015年3月31日 上午9:54:57
     * @Title:
     * @Description: 将指定地址的图片压缩并覆盖原图
     * @ModifiedBy:
     * @param imagePath
     *            图片地址
     * @param maxSize
     *            图片最大大小（单位/kb）
     * @return 是否压缩了
     */
    public static boolean compressImage(String imagePath, int maxSize) {
        if (imagePath == null) {
            return false;
        }
        Bitmap bitmap = null;
        ByteArrayOutputStream baos = null;
        FileOutputStream fos = null;
        try {
            bitmap = BitmapFactory.decodeFile(imagePath);
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while (baos.toByteArray().length > (maxSize * 1024)) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();// 重置baos即清空baos
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                options -= 10;// 每次都减少10
            }
            fos = new FileOutputStream(imagePath);
            fos.write(baos.toByteArray());
            fos.flush();
            if (bitmap != null) {
                bitmap.recycle();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(baos);
            closeStream(fos);
        }
        return false;
    }

    /**
     * @Author: Lqiang
     * @CreteDate: 2015年3月31日 下午3:15:10
     * @Title:
     * @Description: 讲图片按最大size保存到文件
     * @ModifiedBy:
     * @param bitmap
     * @param maxSize
     * @param desPath
     */
    public static void saveBitmapAsMaxSize(Bitmap bitmap, int maxSize, String desPath) {
        if (bitmap == null) {
            return;
        }
        ByteArrayOutputStream baos = null;
        FileOutputStream fos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while (baos.toByteArray().length > (maxSize * 1024)) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();// 重置baos即清空baos
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                options -= 10;// 每次都减少10
            }
            File file = new File(desPath);
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(baos);
            closeStream(fos);
        }
    }

    public static void closeStream(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
