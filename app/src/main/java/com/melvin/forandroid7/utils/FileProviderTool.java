package com.melvin.forandroid7.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.io.File;

/**
 * @Author Melvin
 * @CreatedDate 2017/11/30 10:05
 * @Description ${适配安卓7.0权限uri问题}
 * @Update by       Melvin
 * @Date 2017/11/30 10:05
 * @Description ${TODO}
 */

public class FileProviderTool {

    /**
     * 根据安卓版本来获取uri
     * 这里只判断了小于24的时候用老方法获取uri，如果你还是用了fileprovide的API（getUriForFile）来获取uri，
     * 那么就需要使用grantUriPermission(String toPackage, Uri uri,int modeFlags)来授权，原因可以查看hongyang博客
     * http://blog.csdn.net/lmj623565791/article/details/72859156
     * @param context
     * @param file
     * @return
     */
    public static Uri getUriForFile(Context context, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = getUriForFileFor24(context, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    /**
     * 安卓7.0上获取uri方法
     * @param context
     * @param file
     * @return
     */
    public static Uri getUriForFileFor24(Context context, File file) {
        Uri fileUri = android.support.v4.content.FileProvider.getUriForFile(context,
                context.getPackageName() + ".android7.fileprovider",
                file);
        return fileUri;
    }


    /**
     * 添加对uri文件的读写权限
     * @param context
     * @param intent
     * @param type
     * @param file
     * @param writeAble
     */
    public static void setIntentDataAndType(Context context,
                                            Intent intent,
                                            String type,
                                            File file,
                                            boolean writeAble) {
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setDataAndType(getUriForFile(context, file), type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
    }
}
