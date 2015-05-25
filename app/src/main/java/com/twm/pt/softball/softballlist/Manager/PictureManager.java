package com.twm.pt.softball.softballlist.Manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class PictureManager {

    private static PictureManager mPictureManager = null;
    private Activity mActivity;

    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// Take photo
    private static final int PHOTO_REQUEST_GALLERYPHOTO = 2;// Gallery photo
    private static final int PHOTO_REQUEST_CUTPHOTO = 3;// Cut photo

    private File photoFilePath;
    private String photoFileName;
    private File photoFile;
    private int aspectX=1, aspectY=1, outputX=300, outputY=300;
    private boolean needCut = true;

    private static final String TAG = "PictureManager";

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
     * Take photo
     */
    public void takePhoto() {
        photoFile = new File(photoFilePath, photoFileName);
        if(mActivity!=null) {
            Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            mActivity.startActivityForResult(cameraintent, PHOTO_REQUEST_TAKEPHOTO);
        }
    }

    /**
     * Gallery photo
     */
    public void galleryPhoto() {
        photoFile = new File(photoFilePath, photoFileName);
        if(mActivity!=null) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            mActivity.startActivityForResult(intent, PHOTO_REQUEST_GALLERYPHOTO);
        }
    }

    /**
     * Cut photo
     * @param uri
     */
    private void cutPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");

        // aspectX aspectY: w/h rate
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);

        // outputX,outputY: cut w/h
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
            case PHOTO_REQUEST_TAKEPHOTO:
                if(resultCode==Activity.RESULT_OK) {
                    photoBitmap = fileToBitmap(photoFile.getAbsolutePath());
                    if(needCut) {
                        cutPhoto(Uri.fromFile(photoFile));
                    }
                }
                break;
            case PHOTO_REQUEST_GALLERYPHOTO:
                if (data != null)
                    photoBitmap = getBitmapFromUri(data.getData());
                    if(needCut) {
                        cutPhoto(data.getData());
                    }
                break;
            case PHOTO_REQUEST_CUTPHOTO:
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

    public static boolean saveBitmapToFile(Bitmap photoBitmap, File saveFile) {
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
            Log.d(TAG, "Save file ok!");
            return true;
        }
        return false;
    }

    private Bitmap fileToBitmap(String photoPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
        return bitmap;
    }

    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mActivity.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }


    public static void shareURI(Context mContext, Uri uri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        mContext.startActivity(Intent.createChooser(intent, "Share Cover Image"));
    }


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
     * @param photoFileName
     * @return
     */
    public PictureManager setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
        return this;
    }

    /**
     * @return
     */
    public PictureManager nextPhotoFileName() {
        this.photoFileName = genPhotoFileName();
        return this;
    }

    public boolean isNeedCut() {
        return needCut;
    }

    public PictureManager setNeedCut(boolean needCut) {
        this.needCut = needCut;
        return this;
    }

    //Cut parameter

    /**
     * Width Rate
     * @param aspectX
     * @return
     */
    public PictureManager setAspectX(int aspectX) {
        this.aspectX = aspectX;
        return this;
    }
    /**
     * Height Rate
     * @param aspectY
     * @return
     */
    public PictureManager setAspectY(int aspectY) {
        this.aspectY = aspectY;
        return this;
    }

    /**
     * Cut Width
     * @param outputX
     * @return
     */
    public PictureManager setOutputX(int outputX) {
        this.outputX = outputX;
        return this;
    }

    /**
     * Cut Height
     * @param outputY
     * @return
     */
    public PictureManager setOutputY(int outputY) {
        this.outputY = outputY;
        return this;
    }

}
