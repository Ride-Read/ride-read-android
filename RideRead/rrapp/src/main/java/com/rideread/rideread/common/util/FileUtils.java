package com.rideread.rideread.common.util;

import android.os.Environment;

import com.rideread.rideread.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by SkyXiao on 2017/4/4.
 */

public class FileUtils {

    public final static String APP_SD_ROOT= Environment.getExternalStorageDirectory()+"/RideRead/";

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public static File getFileByPath(String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean isSpace(String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(File file) {
        if (file == null) return false;
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param dirPath 目录路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 复制文件(以超快的速度复制文件)
     *
     * @param srcFile     源文件File
     * @param destDir     目标目录File
     * @param newFileName 新文件名
     * @return 实际复制的字节数，如果文件、目录不存在、文件为null或者发生IO异常，返回-1
     */
    public static long copyFile(File srcFile, File destDir, String newFileName, CopyFileListener listener) {
        long copySizes = 0;
        if (!srcFile.exists()) {
            if (listener != null)
                listener.exception(Utils.getString(R.string.source_file_not_exist));
            copySizes = -1;
        } else if (!destDir.exists()) {
            if (listener != null)
                listener.exception(Utils.getString(R.string.destination_dir_not_exist));
            copySizes = -1;
        } else if (newFileName == null) {
            if (listener != null)
                listener.exception(Utils.getString(R.string.filename_null));
            copySizes = -1;
        } else {
            try {
                File dstFile = new File(destDir, newFileName);

                FileChannel fcin  = new FileInputStream(srcFile).getChannel();
                FileChannel fcout = new FileOutputStream(dstFile).getChannel();
                long        size  = fcin.size();
                fcin.transferTo(0, fcin.size(), fcout);
                fcin.close();
                fcout.close();
                copySizes = size;

                if (listener != null)
                    listener.success(dstFile.getPath());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return copySizes;
    }


    public interface CopyFileListener {
        void exception(String msg);

        void success(String path);
    }
}
