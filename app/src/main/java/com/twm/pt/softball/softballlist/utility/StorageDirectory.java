package com.twm.pt.softball.softballlist.utility;


import android.content.Context;
import android.os.Environment;

import java.io.File;

public class StorageDirectory {

    public enum StorageType {
        ST_Rom_DataDir, // Android Rom 中data目錄
        ST_Rom_AppDir, // Android Rom 中data下app可操作目錄
        ST_SDCard_RootDir, // 最大的SD卡的根目錄
    }

    public static File getStorageDirectory(Context mContext, StorageType st) {
        File f = null;

        if (st.equals(StorageType.ST_Rom_DataDir)) {
            f = Environment.getDataDirectory();
        } else if (st.equals(StorageType.ST_Rom_AppDir) && mContext!=null) {
            f = mContext.getApplicationContext().getFilesDir();
        } else if (st.equals(StorageType.ST_SDCard_RootDir)) {
            String sysESDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            f = Environment.getExternalStorageDirectory();
        }
        return f;
    }

    /**
     * 判斷手機是否有SD卡。
     *
     * @return 有SD卡返回true，沒有返回false。
     */
    public static boolean hasSDCard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
            return false;
        } else {
            return false;
        }
    }

    /**
     * 獲取圖片的本機存放區路徑。
     *
     * @return 圖片的本機存放區路徑。
     */
    public static String getPath(Context mContext) {
        String path = "";
        //沒有sd卡存放到STRom_dataDir下
        if (!hasSDCard()) {
            File f = getStorageDirectory(mContext, StorageType.ST_Rom_DataDir);
            path = f.getAbsolutePath();
        } else {
            File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            if (!f.exists()) {
                //如果沒有DCIM目錄, 就放到sd卡
                f = Environment.getExternalStorageDirectory();
            }
            path = f.getPath() + "/" + "Pictures";
        }

        return path;
    }



    /**
     * 檢查sdk許可權，檢查預設照片存儲路徑是否存在，不在則創建
     *
     * @return
     */
    public static boolean checkPath(Context mContext) {
        try {
            if(mContext==null) return false;
            String path = getPath(mContext);
            return checkPath(path);
        } catch (Exception e) {
        }
        return false;
    }


    /**
     * 檢查sdk許可權，檢查預設照片存儲路徑是否存在，不在則創建
     * @param path
     * @return
     */
    public static boolean checkPath(String path) {
        try {
            if(path==null) return false;
            File file = new File(path);
            return checkPath(file);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 檢查sdk許可權，檢查預設照片存儲路徑是否存在，不在則創建
     * @param file
     * @return
     */
    public static boolean checkPath(File file) {
        try {
            if (file!=null && !file.exists()) {
                file.mkdir();
            }
            return true;
        } catch (Exception e) {
        }
        return false;
    }
}
