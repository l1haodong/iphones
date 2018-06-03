package com.example.iphotos.tools;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.LoginFilter;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import android.Manifest;

import com.example.iphotos.activity.LoginActivity;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zhqy on 2017/9/8.
 */

public class ServerConnector {

    public static final String VERSION_NAME="1.0.0";  //版本名
    public static final int VERSION_CODE=1;  //版本号

    //api key
    private final String appCode="043b218ced6e4504b8b976c5a07f00e5"; //阿里云
    private final String appKey="2621e4d99e99494faa04018d9a2e9cbd";  //图灵

    //服务器地址
    //private final String host = "http://211.87.178.170:8082/";
    //private final String host = "http://180.201.137.235:8000/";
    private final String host = "http://106.15.191.61:8001/";

    //其他操作
    private final String site_icon="img/logo.png";  //logo图片
    private final String site_apk="apk/efficient.apk";  //新版apk

    //GET操作
    private final String action_getTime="time/now";  //获取服务器时间
    private final String action_getVersion="version/getversion";  //获取最新版本信息
    private final String action_getGradeDetail_10="grade/gettop10record";  //获取近10条学分绩记录
    private final String action_check_varify="message/test";

    //POST操作
    private final String action_login="jwt_login/";  //登录
    //private final String action_login="photos/";  //
    private final String action_register="users/";  //注册
    private final String action_bind="user/bind";  //绑定学号
    private final String action_resetPassword="user/reset";  //重置密码
    private final String action_gradeChange="grade/change";  //学分绩变化
    private final String action_feedback="feedback/dofeedback";  //反馈
    private final String action_msg_register="code/";  //注册短信验证码
    private final String action_msg_forget_password="message/forget";  //忘记密码短信验证码
    private final String action_forget_password="user/changepasswd";  //忘记密码重置密码
    private final String action_msg_bind_phone="message/changephone"; //绑定手机短信验证码
    private final String action_bind_phone="user/changephone";  //重绑手机
    private final String action_send_analysis="time/analysis";  //发送分析数据
    private final String action_remind="user/remind";  //设置用户提醒
    private final String action_query_score="stu/grade;";  //查询成绩
    private final String action_query_classroom="stu/classroom;";  //查询成绩
    private final String action_query_timetable="stu/course;";

    private static ServerConnector sConnector=new ServerConnector();

    //单例
    public static ServerConnector getInstance(){
        return sConnector;
    }

    private OkHttpClient client;

    private ServerConnector(){
        client=new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .build();
    }

    /*
    //获取icon的url路径
    public String getIconUrl(){
        return host+site_icon;
    }

    //获取最新版安装包的url路径
    public String getApkUrl(){
        return host+site_apk;
    }

    //获取指定位置横幅图片的url
    public String getBannerImageUrl(int p){
        return host+"img/banner_image_"+String.valueOf(p)+".jpg";
    }

    //获取服务器时间 GET
    public void getServerTime(Callback callback){
        Request request=new Request.Builder()
                .url(host+action_getTime)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //获取最新版本 GET
    public void getVersion(Callback callback){
        Request request=new Request.Builder()
                .url(host+action_getVersion)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //获取最近10条学分绩明细 GET
    public void getUserGradeDetail(String username,Callback callback){
        Request request=new Request.Builder()
                .url(host+action_getGradeDetail_10+"?username="+username)
                .build();
        client.newCall(request).enqueue(callback);
    }

    */
    //用户登录 POST
    public void sendLoginMsg(String username,String password,Callback callback){
        RequestBody requestBody=new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .build();

        /*
        MediaType type=MediaType.parse("image/png");//"text/xml;charset=utf-8"
            File file=new File("/storage/emulated/0/icon.png");
            if (file == null) {
                Log.e("SC", "File is null.");
            }
            RequestBody fileBody=RequestBody.create(type,file);
            //RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png"))
            RequestBody multipartBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file","icon.png",fileBody)//?????
                    .addFormDataPart("name","123")
                    .addFormDataPart("upload_time","2018-01-01 12:12")
                    .addFormDataPart("status","123")
                    .addFormDataPart("description","123")
                    .addFormDataPart("selected_times","123")
                    .addFormDataPart("is_scenic","false")
                    .addFormDataPart("is_character","false")
                    .addFormDataPart("is_book","false")
                    .addFormDataPart("is_cartoon","false")
                    .addFormDataPart("user","1")
                    .addFormDataPart("folder","123")
                    .build();
*/
         /*   RequestBody requestBody1 = new FormBody.Builder()
                    .add("name","123")
                    //.add("file","D:\\151.ppt\\timg.jpg")//?????
                    .add("upload_time","2018-01-01 12:12")
                    .add("status","123")
                    .add("description","123")
                    .add("selected_times","123")
                    .add("is_scenic","false")
                    .add("is_character","false")
                    .add("is_book","false")
                    .add("is_cartoon","false")
                    .add("user","1")
                    .add("folder","123")
                    .build();
                    */
            //requestIncludeRequestBody(action_login,multipartBody,callback);
            requestIncludeRequestBody(action_login,requestBody,callback);

    }
    //}

    //新用户注册 POST
    public void sendRegisterMsg(String username,String password,String code,
                                       Callback callback){
        RequestBody requestBody;
        requestBody=new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .add("code",code)
                .build();
        requestIncludeRequestBody(action_register,requestBody,callback);
        //Log.e("REGISTER", username);
        //Log.e("REGISTER", password);
        //Log.e("REGISTER", code);

    }

    //忘记密码后重置密码
    public void sendForgetPasswordMsg(String username,String password,String code,
                                Callback callback){
        RequestBody requestBody;
        requestBody=new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .add("code",code)
                .build();
        //requestIncludeRequestBody(action_register,requestBody,callback);
        //Log.e("REGISTER", username);
        //Log.e("REGISTER", password);
        //Log.e("REGISTER", code);

    }
    /*
    //绑定学号 POST
    public void sendBindSIDMsg(String username,String sid,String spwd,Callback callback){
        RequestBody requestBody=new FormBody.Builder()
                .add("username",username)
                .add("studentId",sid)
                .add("ipassword",spwd)
                .build();

        requestIncludeRequestBody(action_bind,requestBody,callback);
    }
*/
    //重置密码 POST
    public void sendResetPasswordMsg(String username,String password,
                                            String newPassword,Callback callback){
        RequestBody requestBody=new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .add("newpassword",newPassword)
                .build();

        requestIncludeRequestBody(action_resetPassword,requestBody,callback);
    }
/*
    //学分绩变化 POST
    public void sendGradeChange(String username, double gradeChange, String detail){
        RequestBody requestBody = new FormBody.Builder()
                .add("username",username)
                .add("changes",String.valueOf(gradeChange))
                .add("detail",detail)
                .build();
        Request request = new Request.Builder()
                .url(host + action_gradeChange)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("SC","failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("SC", response.body().string());
            }
        });
    }

    //用户反馈 POST
    public void sendUserFeedback(String username,String type, String detail,
                                        String contact,Callback callback){

        RequestBody requestBody=new FormBody.Builder()
                .add("username",username)
                .add("type",type)
                .add("detail",detail)
                .add("contact",contact)
                .build();

        requestIncludeRequestBody(action_feedback,requestBody,callback);
    }
*/
    //发送注册短信验证码 POST
    public void sendRegisterShortMsg(String phone,Callback callback){
        RequestBody requestBody=new FormBody.Builder()
                .add("phone",phone)
                .build();

        requestIncludeRequestBody(action_msg_register,requestBody,callback);
    }
    //发送重置密码短信验证码 POST
    public void sendForgetPasswordShortMsg(String phone,Callback callback){
        RequestBody requestBody=new FormBody.Builder()
                .add("phone",phone)
                .build();

        //requestIncludeRequestBody(action_msg_register,requestBody,callback);
    }

/*
    //发送忘记密码短信验证码 POST
    public void sendForgetPasswordShortMsg(String username,Callback callback){
        RequestBody requestBody=new FormBody.Builder()
                .add("username",username)
                .build();

        requestIncludeRequestBody(action_msg_forget_password,requestBody,callback);
    }

    //发送重绑手机验证码 POST
    public void sendBindPhoneMsg(String username,String phone,Callback callback){
        RequestBody requestBody=new FormBody.Builder()
                .add("username",username)
                .add("phone",phone)
                .build();

        requestIncludeRequestBody(action_msg_bind_phone,requestBody,callback);
    }

    //发送重绑手机验证码 POST
    public void bindPhone(String username,String phone,Callback callback){
        RequestBody requestBody=new FormBody.Builder()
                .add("username",username)
                .add("phone",phone)
                .build();

        requestIncludeRequestBody(action_bind_phone,requestBody,callback);
    }

    //验证码校验 GET
    public void checkVarify(String username,String verify,Callback callback){
        String url=host+action_check_varify+"?username="+username+"&code="+verify;
        Request request=new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(callback);
    }

    //忘记密码重置密码 POST
    public void forgetPasswordReset(String username,String password,Callback callback){
        RequestBody requestBody=new FormBody.Builder()
                .add("username",username)
                .add("passwd",password)
                .build();

        requestIncludeRequestBody(action_forget_password,requestBody,callback);
    }

    //上传分析数据
    public void sendAnalysisData(String json, Callback callback){
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        requestIncludeRequestBody(action_send_analysis,requestBody,callback);
    }

    //设置用户提醒
    public void setUserRemind(String username,boolean remind,Callback callback){
        RequestBody requestBody=new FormBody.Builder()
                .add("username",username)
                .add("remind",remind==true?"true":"false")
                .build();

        requestIncludeRequestBody(action_remind,requestBody,callback);
    }

    //查询成绩
    public void queryScore(String username,Callback callback){
        String url=host+action_query_score + "?username=" + username;
        Request request=new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void queryClassroom(Callback callback){
        String url=host+action_query_classroom;
        Request request=new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void queryTimetable(String username, Callback callback){
        String url=host + action_query_timetable + "?username=" + username;
        Request request=new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }
    */

    //POST请求封装方法
    private void requestIncludeRequestBody(String action,RequestBody requestBody,Callback callback){
        //String request1 ="{\"photo_name\":\"123\",\"photo_path\": \"D:\\\\151.ppt\",\"photo_upload_time\": \"2018-01-01 12:12\"," +
         //       "    \"photo_status\": null,\"photo_description\": \"123\",\"photo_selected_times\": null," +
          //      "    \"photo_is_scenic\": false,\"photo_is_character\": false,\"photo_is_book\": false," +
           //     "    \"photo_is_cartoon\": false,\"photo_user\": null}";


        Request request=new Request.Builder()
                .url(host+action)
                // .addHeader("User-Agent","android")
                //.header("Content-Type","text/html;charset=utf-8;")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

    /*
    //新闻API
    public void connectNewsAPI(Callback callback){
        Request request=new Request.Builder()
                .url("http://toutiao-ali.juheapi.com/toutiao/index?type=keji")
                .addHeader("Authorization","APPCODE "+appCode)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //天气API
    public void connectWeatherAPI(Callback callback){
        Request request=new Request.Builder()
                .url("http://jisutqybmf.market.alicloudapi.com/weather/query?cityid=283")
                .addHeader("Authorization","APPCODE "+appCode)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //图灵API
    public void connectRobotAPI(String content,Callback callback){
        Request request=new Request.Builder()
                .url("http://www.tuling123.com/openapi/api?key="+appKey+"&info="+content)
                .build();
        client.newCall(request).enqueue(callback);
    }
    */

}
