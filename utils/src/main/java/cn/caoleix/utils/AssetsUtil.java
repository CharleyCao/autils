package cn.caoleix.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author CAOLEI
 *
 * @what android资源文件夹assets的工具类
 *
 */
public class AssetsUtil {

    private AssetsUtil() {}

    public static InputStream getAsStream(String filePath) {
        return AssetsUtil.class.getResourceAsStream("/assets/" + filePath);
    }

    private static Properties getPropFromAssets(String filePath) {
        try {
            InputStream in = getAsStream(filePath);
            Properties props = new Properties();
            props.load(in);
            in.close();
            return props;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取assets文件夹下对应文件的值，根据key获取value
     */
    public static String get(String filePath, String key) {
        return (String)getPropFromAssets(filePath).get(key);
    }

    public static String getAsString(String filePath) {
        InputStream is = getAsStream(filePath);
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, len));
            }
            is.close();
            return sb.toString();
        } catch (IOException exception) {
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
