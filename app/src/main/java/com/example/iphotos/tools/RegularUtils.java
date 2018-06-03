package com.example.iphotos.tools;

/**
 * Created by 李浩东 on 2018/4/14.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检测手机号码，邮箱等是否有效
 * Created by xiaolijuan on 2016/8/21.
 */
public class RegularUtils {
    /**
     * 要更加准确的匹配手机号码只匹配11位数字是不够的，比如说就没有以144开始的号码段，
     * 故先要整清楚现在已经开放了多少个号码段，国家号码段分配如下：
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186 　　电信：133、153、180、189、（1349卫通）
     */
    public static boolean isPhone(String param) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(178)|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(param);
        return m.matches();
    }

    /**
     * 判断email格式是否正确
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        //\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isPasswordValid(String pwd) {
        String str = "^[a-zA-Z0-9]{6,21}$";
        //^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$
        //^[a-zA-Z0-9]{6,16}$
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(pwd);
        return m.matches();
    }
}

