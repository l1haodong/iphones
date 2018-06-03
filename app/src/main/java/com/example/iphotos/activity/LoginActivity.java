package com.example.iphotos.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iphotos.R;
import com.example.iphotos.entity.User;
import com.example.iphotos.tools.EncryptionDevice;
import com.example.iphotos.tools.RegularUtils;
import com.example.iphotos.tools.ServerConnector;
import com.example.iphotos.tools.UserLib;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    /**
     * false清除up文件数据，true不清除
     * 登录成功或网络异常时为true，用户名密码错误时为false
     */
    private boolean fileStatus=true;
    private EditText phoneEdit;
    private EditText passwordEdit;
    private TextInputLayout login_phoneLayout;
    private TextInputLayout login_passwordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initVariable();//初始化变量和按钮
        initButton();
    }
    //重启时，先从文件中获取用户名和密码
    @Override
    protected void onResume() {
        super.onResume();
        getUPFromFile();
    }

    private void initVariable(){
        phoneEdit=(EditText)findViewById(R.id.login_phoneEdit);
        passwordEdit=(EditText)findViewById(R.id.login_passwordEdit);
        login_phoneLayout=(TextInputLayout)findViewById(R.id.login_phoneLayout);
        login_passwordLayout=(TextInputLayout)findViewById(R.id.login_passwordLayout);

        phoneEdit.addTextChangedListener(new MyTextWatcher(phoneEdit));
        passwordEdit.addTextChangedListener(new MyTextWatcher(passwordEdit));
    }

    //按钮初始化
    private void initButton(){
        Button loginButton=(Button)findViewById(R.id.login_loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone1=phoneEdit.getText().toString();
                String password1=passwordEdit.getText().toString();

                if (!isPhoneValid()) {
                    showMessage(getString(R.string.error_phone));
                    return;
                }
                if (!isPasswordValid()) {
                    showMessage(getString(R.string.error_pwd));
                    return;
                }
                logonValidate(phone1,password1);
            }
        });

        TextView forgetPassword=(TextView)findViewById(R.id.login_forgetPassword);
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                Intent intent = new Intent(LoginActivity.this, LockActivity.class);
                startActivity(intent);
            }
        });

        TextView registerAccount=(TextView)findViewById(R.id.login_registerAccount);
        registerAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }



    /**
     * 检查输入的手机号码是否为空以及格式是否正确
     *
     * @return
     */
    public boolean isPhoneValid() {
        String phone = phoneEdit.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || !RegularUtils.isPhone(phone)) {
            login_phoneLayout.setErrorEnabled(true);
            login_phoneLayout.setError(getString(R.string.error_phone));
            phoneEdit.requestFocus();
            return false;
        }
        login_phoneLayout.setErrorEnabled(false);
        return true;
    }

    /**
     * 检查输入的密码是否为空
     *
     * @return
     */
    public boolean isPasswordValid() {
        String pwd = passwordEdit.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            login_passwordLayout.setErrorEnabled(true);
            login_passwordLayout.setError(getResources().getString(R.string.error_pwd));
            passwordEdit.requestFocus();
            return false;
        }
        login_passwordLayout.setErrorEnabled(false);
        return true;
    }

    //动态监听输入过程
    private class MyTextWatcher implements TextWatcher {

        private View view;
        private MyTextWatcher(View view) {
            this.view = view;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.login_phoneEdit:
                    isPhoneValid();
                    break;
                case R.id.login_passwordEdit:
                    isPasswordValid();
                    break;
            }
        }
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //登录验证
    private void logonValidate(final String username, final String password){
        final ProgressDialog pd=new ProgressDialog(LoginActivity.this);
        pd.setTitle("登录");
        pd.setMessage("登录中，请稍候...");
        pd.setCancelable(false);
        pd.show();
        ServerConnector.getInstance().sendLoginMsg(username, password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this,"网络异常，请检查网络设置",
                                Toast.LENGTH_SHORT).show();
                        fileStatus=true;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData =response.body().string();
                //Log.e("LOGIN", responseData);
                //Log.e("LOGIN", String.valueOf(response.code()));
                try{
                    JSONObject jsonObject=new JSONObject(responseData);
                    //String reason = jsonObject.getString("non_field_errors");
                    int code =response.code();
                    if( code == 400 ){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                Toast.makeText(LoginActivity.this,"用户名或密码错误",
                                        Toast.LENGTH_SHORT).show();
                                fileStatus=false;
                            }
                        });

                    }
                    else{
                        pd.dismiss();
                        //String email=jsonObject.getString("email");
                        //String phone=jsonObject.getString("phone");

                        //设置单例类型
                        User user= UserLib.getInstance().getUser();
                        user.setUsername(username);
                        user.setPassword(password);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                Toast.makeText(LoginActivity.this,"欢迎登陆",
                                        Toast.LENGTH_SHORT).show();
                                fileStatus=false;
                            }
                        });
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                        finish();
                        saveUPInFile(username,password);
                        fileStatus=true;
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(LoginActivity.this,"登陆异常",
                                    Toast.LENGTH_SHORT).show();
                            fileStatus=false;
                        }
                    });
                }
            }
        });
    }

    //用户名密码写入文件
    private void saveUPInFile(String username,String password){
        SharedPreferences.Editor editor=getSharedPreferences("data_up",0).edit();
        editor.putString("username",username);
        editor.putString("password", EncryptionDevice.encrypt(password));
        editor.apply();
    }

    //从文件中读用户名密码
    private void getUPFromFile(){
        SharedPreferences pref=getSharedPreferences("data_up",0);
        SharedPreferences.Editor editor=pref.edit();

        String username=pref.getString("username","");
        String encodedPassword=pref.getString("password","");//对用户名不加密，对密码加密
        String password=EncryptionDevice.decipher(encodedPassword);

        //当读取的用户名和密码不为空时，设置到输入框上
        if(!(username.equals("")&&password.equals(""))){
            phoneEdit.setText(username);
            passwordEdit.setText(password);
            logonValidate(username, password);
            //false清除up文件数据，true不清除
            if(!fileStatus){
                editor.clear();
                editor.apply();
            }
        }
    }
}

