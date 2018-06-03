package com.example.iphotos.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 李浩东 on 2018/5/1.
 */

public class DirUtil {
    public static String getPhotoTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(new Date(time * 1000));
    }
}
