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

    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// ���
    private static final int PHOTO_REQUEST_GALLERYPHOTO = 2;// �q�ۥU�����
    private static final int PHOTO_REQUEST_CUTPHOTO = 3;// ���G

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
     * ���o��ӷӤ�
     */
    public void takePhoto() {
        photoFile = new File(photoFilePath, photoFileName);
        if(mActivity!=null) {
            Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // ���w�եά۾���ӫ�Ӥ����x�s���|
            cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            mActivity.startActivityForResult(cameraintent, PHOTO_REQUEST_TAKEPHOTO);
        }
    }

    public void galleryPhoto() {
        photoFile = new File(photoFilePath, photoFileName);
        if(mActivity!=null) {
            //�}�Ҭ�ï�ۤ����A����startActivityForResult�B�a�JrequestCode�i��I�s�A��]���I��ۤ����^�{���I�sonActivityResult
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            mActivity.startActivityForResult(intent, PHOTO_REQUEST_GALLERYPHOTO);
        }
    }

    /**
     * �ŵ��Ϥ�
     * @param uri
     */
    private void cutPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop?true�O�]�m�b�}?��intent���]�m��ܪ�view�i�H�ŵ�
        intent.putExtra("crop", "true");

        // aspectX aspectY �O�e�������
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);

        // outputX,outputY �O�ŵ��Ϥ����e��
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
            case PHOTO_REQUEST_TAKEPHOTO:// ���ܩ�Ӯɽե�
                if(resultCode==Activity.RESULT_OK) {
                    cutPhoto(Uri.fromFile(photoFile));
                }
                break;
            case PHOTO_REQUEST_GALLERYPHOTO:// ���ܱq���a����Ϥ���
                // ���D�ŧP�_�A��ڭ�ı�o�����N�Q���s�ŵ����ɭԫK���|�����`�A�U�P
                if (data != null)
                    cutPhoto(data.getData());
                break;
            case PHOTO_REQUEST_CUTPHOTO:// ��^�����G
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


    // �ϥΨt�η�e����[�H�վ�@?�Ӥ����W��
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
     * �]�w���|
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
     * �]�w�ɦW
     * @param photoFileName
     * @return
     */
    public PictureManager setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
        return this;
    }

    /**
     * �̮ɶ��۰ʲ����ɦW
     * @return
     */
    public PictureManager nextPhotoFileName() {
        this.photoFileName = genPhotoFileName();
        return this;
    }

    //Cut parameter

    /**
     * �e�����
     * @param aspectX
     * @return
     */
    public PictureManager setAspectX(int aspectX) {
        this.aspectX = aspectX;
        return this;
    }
    /**
     * �������
     * @param aspectY
     * @return
     */
    public PictureManager setAspectY(int aspectY) {
        this.aspectY = aspectY;
        return this;
    }

    /**
     * �ŵ��Ϥ����e
     * @param outputX
     * @return
     */
    public PictureManager setOutputX(int outputX) {
        this.outputX = outputX;
        return this;
    }

    /**
     * �ŵ��Ϥ�����
     * @param outputY
     * @return
     */
    public PictureManager setOutputY(int outputY) {
        this.outputY = outputY;
        return this;
    }

}
