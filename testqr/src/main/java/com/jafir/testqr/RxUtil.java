package com.jafir.testqr;

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.annimon.stream.Optional;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.math.BigDecimal;

import rx.Observable;


/**
 * Created by jafir on 2017/4/11.
 */

public class RxUtil {


    /**
     * 传入context 和filename
     * 规则：如果有sd卡则默认为创建sd卡下文件夹
     * 然后创建文件
     * 如果没有则在应用内部路径下创建文件夹
     * 然后创建文件
     * 最后返回文件rx流
     *
     * @param context
     * @param fileName
     * @return
     */
    public static Observable<File> getTempFile(Context context, String fileName) {
        return RxPermissions.getInstance(context)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .map(granted -> TempPath.unit(context)
                        .createFile(TextUtils.isEmpty(fileName) ? String.valueOf(System.currentTimeMillis()) : fileName));
    }

    /**
     * 清理缓存
     *
     * @param context
     */
    public static void clearCache(Context context) {
        TempPath.clear(context);
    }

    /**
     * 计算缓存目录下面的文件大小
     *
     * @param context
     * @return
     */
    public static String getTotalSize(Context context) {
        return TempPath.getTotalCacheSize(context);
    }

}


/**
 * path 可以是内部和外部路径
 */
abstract class TempPath {

    private static final String LOCAL_PATH = "temp";
    private static final String EXTERNAL_PATH = "/jafir/temp";


    static TempPath unit(Context context) {
        return getExdir()
                .<TempPath>map(ExPath::new)
                .orElseGet(() -> getIndir(context)
                        .map(InPath::new).get()
                );

    }

    static void clear(Context context) {
        getExdir().ifPresent(TempPath::delete);
        getIndir(context).ifPresent(TempPath::delete);
    }


    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children == null || children.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < children.length; i++) {
                //递归调用删除子文件
                delete(children[i]);
            }
            file.delete();

        }
    }

    public static String getTotalCacheSize(Context context) {
        long a = getExdir().map((file) -> cal(file)).get();
        long b = getIndir(context).map((file) -> cal(file)).get();
        String ss = getFormatSize(a + b);
        return ss;
    }

    public static long cal(File file) {
        if (file.isFile()) {
            return file.length();
        }
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children == null || children.length == 0) {
                file.length();
            }

            for (int i = 0; i < children.length; i++) {
                //递归调用计算子文件大小
                cal(children[i]);
            }
            return file.length();
        }
        return 0;
    }


    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            //此方法用于格式化 小数位数
            //表示 如果  2.357 = 2.36   四舍五入
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    static Optional<File> getExdir() {
        File fileDir = null;
        //如果有SD卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = Environment.getExternalStorageDirectory();
            fileDir = new File(dir, EXTERNAL_PATH);
            if (!fileDir.exists()) {
                if (!fileDir.mkdirs()) {
                    fileDir = null;
                }
            }
        }
        return Optional.ofNullable(fileDir);

    }

    static Optional<File> getIndir(Context context) {
        File fileDir = null;
        fileDir = context.getDir(LOCAL_PATH, Context.MODE_PRIVATE);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        Log.d("debuyg", "+" + fileDir.toString());


        return Optional.ofNullable(fileDir);
    }


    abstract File createFile(String filename);

    /**
     * 外部路径
     */
    static class ExPath extends TempPath {

        private File file;

        public ExPath(File file) {
            this.file = file;
        }

        @Override
        File createFile(String fileName) {
            return new File(file, fileName);
        }
    }

    /**
     * 内部路径
     */
    static class InPath extends TempPath {
        private File file;

        public InPath(File file) {
            this.file = file;
        }

        @Override
        File createFile(String fileName) {
            return new File(file, fileName);
        }
    }

}


