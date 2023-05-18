package com.hb.android.tapplication.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresPermission;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;

/**
 * 文件工具类，包含方法：
 * <p> copyToAppFilesByUri(),通过Uri把选择的文件复制到data目录下;
 * <p> copyToAppFilesByPath(),通过文件的路径把选择的文件复制到data目录下去;
 * <p> copyAssetsFileToAppFiles(),从assets目录中复制某文件内容到data目录下去;
 * <p> delDirectoryFile(),删除文件夹;
 * <p> getFilePathByUri(),通过文件的Uri获得它的真实路径;
 * <p> isExternalStorageDocument(),判断Uri是不是externalstorage.documents类型;
 * <p> isDownloadsDocument(),判断Uri是不是downloads.documents类型;
 * <p> isMediaDocument(),判断Uri是不是media.documents类型;
 * <p> queryFileDetailFromUri(),通过图片的uri查询图片的所有详细信息通过Log.i输出
 */
public class FileUtil {
    private static final String TAG = "FileUtil测试";

    /**
     * 通过Uri把选择的文件复制到data目录下去，
     * 使用InputStream is = context.openFileInput("文件名,不需要路径");即可读取文件.
     *
     * @param c           Context
     * @param uri         选择的文件的uri
     * @param newFileName 复制到/data/data/package_name/files/目录下的文件的名字
     */
    public static void copyToAppFilesByUri(Context c, Uri uri, String newFileName) {
        // 如果文件已经存在data目录下，直接删除重新复制
        String newFilePath = c.getFilesDir().getAbsolutePath() + File.separator + newFileName;
        File newFile = new File(newFilePath);
        if (newFile.exists()) {
            try {
                // 判断是否是文件夹
                if (newFile.isDirectory()) {
                    Log.e(TAG, "目标文件是一个已存在的文件夹，请更改文件名");
                    return;
                } else {
                    newFile.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        InputStream is = null;
        FileOutputStream fos = null;
        int buffSize = 1024;
        try {
            is = c.getContentResolver().openInputStream(uri);
            fos = c.openFileOutput(newFileName, Context.MODE_PRIVATE);
            int byteCount;
            byte[] buffer = new byte[buffSize];
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过文件的路径把选择的文件复制到data目录下去，此方法需要权限android.permission.WRITE_EXTERNAL_STORAGE。
     * <p> 若已经在其他地方申请权限，请使用@SuppressLint("MissingPermission")忽略。
     * <p> 使用InputStream is = context.openFileInput("文件名,不需要路径");即可读取文件.
     *
     * @param c           Context
     * @param oldFilePath 选择的文件的路径
     * @param newFileName 复制到/data/data/package_name/files/目录下的文件的名字
     */
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static void copyToAppFilesByPath(Context c, String oldFilePath, String newFileName) {
        // 如果文件已经存在data目录下，直接删除重新复制
        String newFilePath = c.getFilesDir().getAbsolutePath() + File.separator + newFileName;
        File newFile = new File(newFilePath);
        if (newFile.exists()) {
            try {
                // 判断是否是文件夹
                if (newFile.isDirectory()) {
                    Log.e(TAG, "目标文件是一个已存在的文件夹，请更改文件名");
                    return;
                } else {
                    newFile.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        InputStream is = null;
        FileOutputStream fos = null;
        int buffSize = 1024;
        try {
            Uri uri = Uri.fromFile(new File(oldFilePath));
            is = c.getContentResolver().openInputStream(uri);
            fos = c.openFileOutput(newFileName, Context.MODE_PRIVATE);
            int byteCount;
            byte[] buffer = new byte[buffSize];
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从assets目录中复制某文件内容到data目录下去
     *
     * @param c             Context
     * @param assetFileName assets目录下的Apk源文件路径
     * @param newFileName   复制到/data/data/package_name/files/目录下文件名
     */
    public static void copyAssetsFileToAppFiles(Context c, String assetFileName, String newFileName) {
        // 文件是否已经存在data目录下，直接删除重来
        String newFilePath = c.getFilesDir().getAbsolutePath() + File.separator + newFileName;
        File newFile = new File(newFilePath);
        if (newFile.exists()) {
            try {
                // 判断是否是文件夹
                if (newFile.isDirectory()) {
                    Log.e(TAG, "目标文件是一个已存在的文件夹，请更改文件名");
                    return;
                } else {
                    newFile.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        InputStream is = null;
        FileOutputStream fos = null;
        int buffSize = 1024;
        try {
            is = c.getAssets().open(assetFileName);
            fos = c.openFileOutput(newFileName, Context.MODE_PRIVATE);
            int byteCount;
            byte[] buffer = new byte[buffSize];
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
        } catch (
                Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除文件夹
     *
     * @param delFile 要删除的文件夹
     */
    public static void delDirectoryFile(File delFile) {
        //获取目录下的文件数组
        File[] listFiles = delFile.listFiles();
        if (listFiles == null) {
            return;
        }
        for (File f : listFiles) {
            if (f.isDirectory()) {
                //判断是否为文件夹,是则调用递归删除文件夹里面的文件
                delDirectoryFile(f);
            }
            //当文件夹里面的文件删除完毕后,删除文件夹
            try {
                f.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //删除里面的文件夹以及文件后,最后删除该文件夹
        try {
            delFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过文件的Uri获得它的真实路径
     *
     * @param context context
     * @param uri     文件的Uri
     * @return 文件的真实路径
     */
    public static String getFilePathByUri(Context context, Uri uri) {
        String path = null;
        // 以 file:// 开头的
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            path = uri.getPath();
            return path;
        }
        // 以 content:// 开头的，比如 content://media/extenral/images/media/17766
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (columnIndex > -1) {
                        path = cursor.getString(columnIndex);
                    }
                }
                cursor.close();
            }
            return path;
        }
        // 4.4及之后的 是以 content:// 开头的，比如 content://com.android.providers.media.documents/document/image%3A235700
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    // ExternalStorageProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        path = Environment.getExternalStorageDirectory() + "/" + split[1];
                        return path;
                    }
                } else if (isDownloadsDocument(uri)) {
                    // DownloadsProvider
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));
                    path = getDataColumn(context, contentUri, null, null);
                    return path;
                } else if (isMediaDocument(uri)) {
                    // MediaProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    } else {
                        Log.e(TAG, "无法获取此文件的路径");
                        return null;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};
                    path = getDataColumn(context, contentUri, selection, selectionArgs);
                    return path;
                }
            } else {
                path = getDataColumn(context, uri, null, null);
                return path;
            }
        }
        return null;
    }

    /**
     * 根据条件查询文件路径,参数同ContentResolver.query()方法
     *
     * @param context       context
     * @param uri           uri
     * @param selection     selection
     * @param selectionArgs selectionArgs
     * @return 文件路径
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Log.w(TAG, "getDataColumn: uri = " + uri);
        Cursor cursor = null;
        final String column = MediaStore.MediaColumns.DATA;
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * 判断Uri是不是externalstorage.documents类型
     *
     * @param uri Uri
     * @return 是则返回true
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * 判断Uri是不是downloads.documents类型
     *
     * @param uri Uri
     * @return 是则返回true
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * 判断Uri是不是media.documents类型
     *
     * @param uri Uri
     * @return 是则返回true
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    /**
     * 通过图片的uri查询输出文件的所有详细信息
     *
     * @param uri 图片的uri
     */
    @SuppressLint({"Range", "MissingPermission"})
    public static void queryFileDetailFromUri(Context context, Uri uri) {
        String selection = null;
        String[] selectionArgs = null;
        if (isMediaDocument(uri)) {
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            final String type = split[0];
            if ("image".equals(type)) {
                uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
                uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(type)) {
                uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }
            selection = "_id=?";
            selectionArgs = new String[]{split[1]};
        }
        try {
            // 如果需要选取的文件的详细信息（大小、路径、修改时间、MIME、宽高、文件名等）,
            // 则需要通过 .query(uri, ...) 查询（直接查询所有字段）
            Cursor cursor = context.getContentResolver()
                    .query(uri, null, selection, selectionArgs, null);
            // 一般查询出来的只有一条记录
            if (cursor.moveToFirst()) {
                // 查看查询结果数据的的所有列, 不同系统版本列名数量和类型可能不相同, 参考:
                /*[ _id, _data, _size, _display_name, mime_type, title, date_added, date_modified,
                description, picasa_id, isprivate, latitude, longitude, datetaken, orientation,
                mini_thumb_magic, bucket_id, bucket_display_name, width, height]*/
                Log.i("测试，文件信息的所有列", "columnNames: " + Arrays.toString(cursor.getColumnNames()));
                // 获取图片的 大小、文件名、路径
                // long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE));
                // String filename = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));
                // String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                // 输出所有列对应的值
                for (String column : cursor.getColumnNames()) {
                    int index = cursor.getColumnIndex(column);
                    String valueDesc;
                    switch (cursor.getType(index)) {
                        case Cursor.FIELD_TYPE_NULL:
                            valueDesc = column + "：NULL";
                            break;
                        case Cursor.FIELD_TYPE_INTEGER:
                            valueDesc = column + "：" + cursor.getInt(index);
                            break;
                        case Cursor.FIELD_TYPE_FLOAT:
                            valueDesc = column + "：" + cursor.getFloat(index);
                            break;
                        case Cursor.FIELD_TYPE_STRING:
                            valueDesc = column + "：" + cursor.getString(index);
                            break;
                        case Cursor.FIELD_TYPE_BLOB:
                            valueDesc = column + "：BLOB";
                            break;
                        default:
                            valueDesc = column + "：Unknown";
                    }
                    Log.i("测试，文件信息", valueDesc);
                }
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
