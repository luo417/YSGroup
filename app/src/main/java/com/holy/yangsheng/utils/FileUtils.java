package com.holy.yangsheng.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Hailin on 2017/2/12.
 */

public class FileUtils {
    public static final String ROOT = "yangsheng";
    public static final String CACHE = "cache";

    public static File getDir(String fileName) {
        StringBuilder path = new StringBuilder();

        //如果SD卡可用，则保存在在SD卡中，否则保存在内存中
        if (isSDAvailable()) {
            path.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            path.append(File.separator);
            path.append(ROOT);
            path.append(File.separator);
            path.append(fileName);
        } else {
            File dir = UIUtils.getContext().getFilesDir();
            path.append(dir.getAbsolutePath());
        }

        File file = new File(path.toString());
        if (!file.exists() || !file.isDirectory()) {  //此路径不存在或不是文件夹，则创建一个文件夹
            file.mkdirs();
        }

        return file;
    }

    private static boolean isSDAvailable(){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        }else {
            return false;
        }
    }

    public static File getCacheDir(){
        return getDir(CACHE);
    }
}
