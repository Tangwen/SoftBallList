package com.twm.pt.softball.softballlist.Manager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class PictureManager {

    private static PictureManager mPictureManager = null;
    private Activity mActivity;

    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERYPHOTO = 2;// 從相冊中選擇
    private static final int PHOTO_REQUEST_CUTPHOTO = 3;// 結果

    private File photoFilePath;
    private String photoFileName;
    private File photoFile;
    private int aspectX=1, aspectY=1, outputX=300, outputY=300;

    private String TAG = "PictureManager";

    public static PictureManager getInstance(Activity mActivity) {
        if(mPictureManager==null) {
            mPictureManager = new PictureManager(mActivity);
        } else {
            if(!mActivity.equals(mPictureManager.mActivity)) {
                mPictureManager = new PictureManager(mActivity);
            }
        }
        return mPictureManager;
    }


    PictureManager(Activity mActivity) {
        this.mActivity = mActivity;
        photoFilePath = Environment.getExternalStorageDirectory();
        photoFileName = genPhotoFileName();
    }


    /**
     * 取得拍照照片
     */
    public void takePhoto() {
        photoFile = new File(photoFilePath, photoFileName);
        if(mActivity!=null) {
            Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 指定調用相機拍照後照片的儲存路徑
            cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            mActivity.startActivityForResult(cameraintent, PHOTO_REQUEST_TAKEPHOTO);
        }
    }

    public void galleryPhoto() {
        photoFile = new File(photoFilePath, photoFileName);
        if(mActivity!=null) {
            //開啟相簿相片集，須由startActivityForResult且帶入requestCode進行呼叫，原因為點選相片後返回程式呼叫onActivityResult
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            mActivity.startActivityForResult(intent, PHOTO_REQUEST_GALLERYPHOTO);
        }
    }

    /**
     * 剪裁圖片
     * @param uri
     */
    private void cutPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop?true是設置在開?的intent中設置顯示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是寬高的比例
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);

        // outputX,outputY 是剪裁圖片的寬高
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        mActivity.startActivityForResult(intent, PHOTO_REQUEST_CUTPHOTO);
    }


    public Bitmap onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap photoBitmap = null;
        Log.d(TAG, "resultCode=" + resultCode);
        switch (requestCode) {
            case PHOTO_REQUEST_TAKEPHOTO:// 當選擇拍照時調用
                if(resultCode==Activity.RESULT_OK) {
                    cutPhoto(Uri.fromFile(photoFile));
                }
                break;
            case PHOTO_REQUEST_GALLERYPHOTO:// 當選擇從本地獲取圖片時
                // 做非空判斷，當我們覺得不滿意想重新剪裁的時候便不會報異常，下同
                if (data != null)
                    cutPhoto(data.getData());
                break;
            case PHOTO_REQUEST_CUTPHOTO:// 返回的結果
                if (data != null)
                    photoBitmap = getPhotoData(data);
                    saveBitmapToFile(photoBitmap, photoFile);
                break;
        }
        return photoBitmap;
    }


    private Bitmap getPhotoData(Intent photoData) {
        Bitmap photoBitmap = null;
        Bundle bundle = photoData.getExtras();
        if (bundle != null) {
            photoBitmap = bundle.getParcelable("data");
        }
        return photoBitmap;
    }

    private boolean saveBitmapToFile(Bitmap photoBitmap, File saveFile) {
        if(photoBitmap!=null && saveFile!=null) {
            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(saveFile));
                photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                bos.flush();
                bos.close();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                Log.e(TAG, "Save file error!");
                return false;
            }
            Log.d(null, "Save file ok!");
            return true;
        }
        return false;
    }


    // 使用系統當前日期加以調整作?照片的名稱
    private String genPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".png";
    }


    //Photo File
    public File getPhotoFile() {
        return photoFile;
    }

    //Photo File Path
    public File getPhotoFilePath() {
        return photoFilePath;
    }

    /**
     * 設定路徑
     * @param photoFilePath
     * @return
     */
    public PictureManager setPhotoFilePath(File photoFilePath) {
        this.photoFilePath = photoFilePath;
        return this;
    }

    //Photo File Name
    public String getPhotoFileName() {
        return photoFileName;
    }

    /**
     * 設定檔名
     * @param photoFileName
     * @return
     */
    public PictureManager setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
        return this;
    }

    /**
     * 依時間自動產生檔名
     * @return
     */
    public PictureManager nextPhotoFileName() {
        this.photoFileName = genPhotoFileName();
        return this;
    }

    //Cut parameter

    /**
     * 寬的比例
     * @param aspectX
     * @return
     */
    public PictureManager setAspectX(int aspectX) {
        this.aspectX = aspectX;
        return this;
    }
    /**
     * 高的比例
     * @param aspectY
     * @return
     */
    public PictureManager setAspectY(int aspectY) {
        this.aspectY = aspectY;
        return this;
    }

    /**
     * 剪裁圖片的寬
     * @param outputX
     * @return
     */
    public PictureManager setOutputX(int outputX) {
        this.outputX = outputX;
        return this;
    }

    /**
     * 剪裁圖片的高
     * @param outputY
     * @return
     */
    public PictureManager setOutputY(int outputY) {
        this.outputY = outputY;
        return this;
    }

}
