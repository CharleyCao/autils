package cn.caoleix.utils;

import com.google.gson.Gson;

public class JsonUtil {

    private static Gson gson = new Gson();

    public static <T> T parse(String string, Class<T> clz) {
        return gson.fromJson(string, clz);
    }

}
