package com.yunwang.manager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.yunwang.utils.ImageUtils;
import com.yunwang.utils.ScreenUtils;
import com.yunwang.utils.Util;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by a on 2016/11/25.
 */

public class GetPhotoManager {

    private static final String TAG = "GetPhotoManager";
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_PHOTO = 2;
    private static final int REQUEST_CODE_CROP = 3;
    private static final int REQUEST_CODE_KITKAT_PHOTO = 4;
    /**
     * 图片和拍照的缓存文件夹
     */
    private static File imageCacheDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile().toString()+File.separator+"photo");
    /**
     * 拍照存放的图片文件(原图)
     */
    private File image_camera = null;
    /**
     * 裁剪存放的图片文件（压缩后的）
     */
    private File image_crop = null;
    /**
     * 裁剪存放的缩略图片文件（压缩后的）
     */
    private File image_crop_small = null;
    private static String SUFFIX_CAMERA = ".jpg";
    private static String SUFFIX_CROP = ".crop";
    private static String SUFFIX_CROP_SMALL = ".small";
    private String fileName = null;
    /** 裁剪框比例x  */
    private int aspectX = 1;
    /** 裁剪框比例Y  */
    private int aspectY = 1;
    /** 裁剪后图片像素大小x  */
    private int outputX = 720;
    /** 裁剪后图片像素大小y  */
    private int outputY = 720;
    private Activity mContext;

    public GetPhotoManager(Activity context) {
        mContext = context;
        clearPhotoCache();
    }

    /**
     * @Author: Lqiang
     * @CreteDate: 2015年4月15日 上午9:38:35
     * @Title:
     * @Description: 清除图片文件缓存
     * @ModifiedBy:
     */
    private void clearPhotoCache(){
        if(!imageCacheDir.exists()){
            imageCacheDir.mkdirs();
        }
        try {
            File[] listFiles = imageCacheDir.listFiles();
            for (File file : listFiles) {
                if(file.isFile()){
                    file.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author: Lqiang
     * @CreteDate: 2015年3月19日 下午3:35:42
     * @Title:
     * @Description: 拍照获取图片
     * @ModifiedBy:
     */
    public void chooseFromCamera(){
        fileName = UUID.randomUUID().toString();
        image_camera = new File(imageCacheDir, fileName+SUFFIX_CAMERA);
        checkFile(image_camera);
        Intent intent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(image_camera));
        mContext.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    private void checkFile(File file){
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if(file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author: Lqiang
     * @CreteDate: 2015年3月19日 下午3:55:48
     * @Title:
     * @Description: 在onActivityResult中调用处理系统图片调用后的操作
     * @ModifiedBy:
     * @param requestCode
     * @param data
     * @param cropListener 裁剪完后回调
     */
    public void hanlderActivityResult(int requestCode,Intent data,CropListener cropListener){
        switch (requestCode)
        {
            case REQUEST_CODE_CAMERA:
                //notifySystem();
                //判断旋转图片
                fixRotate();
                goCrop(Uri.fromFile(image_camera),aspectX, aspectY, outputX, outputY);
                break;
            case REQUEST_CODE_KITKAT_PHOTO:
                goCrop(data.getData(),aspectX, aspectY, outputX, outputY);
                break;
            case REQUEST_CODE_PHOTO:
                goCrop(data.getData(),aspectX, aspectY, outputX, outputY);
                break;
            case REQUEST_CODE_CROP:
                System.gc();System.gc();System.gc();
                //先压缩图片
                comprassImage(50, 100,80);
                cropListener.onCropAfter(image_crop.getPath(),image_crop_small.getPath());
                break;
        }
    }

    /**
     * @Author: Lqiang
     * @CreteDate: 2015年3月19日 下午3:44:01
     * @Title:
     * @Description: 调用系统裁剪图片
     * @ModifiedBy:
     * @param uri
     * @param aspectX 裁剪框比例 x
     * @param aspectY 裁剪框比例 y
     * @param outputX 裁剪后保存的图片像素x
     * @param outputY 裁剪后保存的图片像素y
     */
    public void goCrop(Uri uri,int aspectX,int aspectY,int outputX,int outputY)
    {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            String url = Util.getPath(mContext, uri);
            intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
        }else{
            intent.setDataAndType(uri, "image/*");
        }
        //裁剪图片
        image_crop =  new File(imageCacheDir, fileName+SUFFIX_CROP);
        checkFile(image_crop);
        //压缩图片
        image_crop_small =  new File(imageCacheDir, fileName+SUFFIX_CROP_SMALL);
        checkFile(image_crop_small);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image_crop));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 设置裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("return-data", false);
        intent.putExtra("scale", true);
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        mContext.startActivityForResult(intent, REQUEST_CODE_CROP);
    }

    /**
     * @Author: Lqiang
     * @CreteDate: 2015年3月31日 下午3:05:54
     * @Title:
     * @Description: 压缩大图片，保存缩略图
     * @ModifiedBy:
     * @param maxSize 大图片最大size 单位/kb
     * @param smallW  缩略图尺寸
     */
    private void comprassImage(int maxSize, int smallW,int smallH) {
        ImageUtils.compressImage(image_crop.getPath(), maxSize);
        ImageUtils.saveBitmapAsMaxSize(ImageUtils.compressImageBySize(ImageUtils.compressImageBySize(image_crop.getPath(), smallW, smallH), smallW, smallH), 10, image_crop_small.getPath());
    }

    private void fixRotate() {
        // 从指定路径下读取图片，并获取其EXIF信息
        Bitmap bitmap = null;
        Bitmap bmp = null;
        FileOutputStream fos = null;
        try {
            int degree = getBitmapDegree(image_camera.getPath());
            if(degree!=0){
                //旋转图片
                bitmap = ImageUtils.compressImageBySize(image_camera.getPath(), ScreenUtils.getScreenPiexWidth(mContext), ScreenUtils.getScreenPiexHeight(mContext));
                bmp = rotateBitmapByDegree(bitmap,degree);
                checkFile(image_camera);
                fos = new FileOutputStream(image_camera.getPath());
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            closeStream(fos);
            if(bmp!=null){
                bmp.recycle();
            }
        }
    }

    public void closeStream(Closeable io){
        if(io!=null){
            try {
                io.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param path
     *            图片绝对路径
     * @return 图片的旋转角度
     */
    private int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm
     *            需要旋转的图片
     * @param degree
     *            旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    public interface CropListener{
        /**
         * @Author: Lqiang
         * @CreteDate: 2015年3月24日 上午10:29:48
         * @Title:
         * @Description:
         * @ModifiedBy:
         * @param imagePath 返回的图片地址
         */
        void onCropAfter(String imagePath,String imageSmallPath);
    }
}
