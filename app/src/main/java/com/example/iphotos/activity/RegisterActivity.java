package com.example.iphotos.activity;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.superscholar.android.tools.ActivityCollector;
import com.example.iphotos.R;
import com.example.iphotos.entity.User;
import com.example.iphotos.tools.ActivityCollector;
import com.example.iphotos.tools.EncryptionDevice;
import com.example.iphotos.tools.RegularUtils;
import com.example.iphotos.tools.ServerConnector;
import com.example.iphotos.tools.UserLib;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText phoneEdit;
    private EditText verifyEdit;
    private EditText passwordEdit;
    private EditText cpasswordEdit;
    //private EditText emailEdit;

    private TextInputLayout register_phoneLayout;
    private TextInputLayout register_verifyLayout;
    private TextInputLayout register_passwordLayout;
    private TextInputLayout register_cpasswordLayout;
    //private TextInputLayout register_emailLayout;

    private Button verifyButton;

    private CountDownTimer timer;

    //变量初始化
    private void initVariable(){
        phoneEdit=(EditText)findViewById(R.id.register_phoneEdit);
        verifyEdit=(EditText)findViewById(R.id.register_verifyEdit);
        passwordEdit=(EditText)findViewById(R.id.register_passwordEdit);
        cpasswordEdit=(EditText)findViewById(R.id.register_cpasswordEdit);
        //emailEdit=(EditText)findViewById(R.id.register_emailEdit);

        register_phoneLayout=(TextInputLayout)findViewById(R.id.register_phoneLayout);
        register_verifyLayout=(TextInputLayout)findViewById(R.id.register_verifyLayout);
        register_passwordLayout=(TextInputLayout)findViewById(R.id.register_passwordLayout);
        register_cpasswordLayout=(TextInputLayout)findViewById(R.id.register_cpasswordLayout);
        //register_emailLayout=(TextInputLayout)findViewById(R.id.register_emailLayout);

        phoneEdit.addTextChangedListener(new RegisterActivity.MyTextWatcher(phoneEdit));
        verifyEdit.addTextChangedListener(new RegisterActivity.MyTextWatcher(verifyEdit));
        passwordEdit.addTextChangedListener(new RegisterActivity.MyTextWatcher(passwordEdit));
        cpasswordEdit.addTextChangedListener(new RegisterActivity.MyTextWatcher(cpasswordEdit));
        //emailEdit.addTextChangedListener(new RegisterActivity.MyTextWatcher(emailEdit));
    }

    //菜单栏初始化
    private void initToolbar(){
        Toolbar toolbar=(Toolbar)findViewById(R.id.register_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //初始化倒计时
    private void initTimer(){
        timer=new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                verifyButton.setText(""+millisUntilFinished/1000+"s");
            }

            @Override
            public void onFinish() {
                verifyButton.setText("获取验证码");
                verifyButton.setEnabled(true);
                verifyButton.setBackgroundColor(Color.parseColor("#1C86EE"));
            }
        };
    }

    /**
     * 检查输入的手机号码是否为空以及格式是否正确
     *
     * @return
     */
    public boolean isPhoneValid() {
        String phone = phoneEdit.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || !RegularUtils.isPhone(phone)) {
            register_phoneLayout.setErrorEnabled(true);
            register_phoneLayout.setError(getString(R.string.error_phone));
            phoneEdit.requestFocus();
            return false;
        }
        register_phoneLayout.setErrorEnabled(false);
        return true;
    }
    public boolean isVerifyValid() {
        String verify = verifyEdit.getText().toString().trim();
        if (TextUtils.isEmpty(verify)) {
            register_verifyLayout.setErrorEnabled(true);
            register_verifyLayout.setError(getResources().getString(R.string.error_verify));
            verifyEdit.requestFocus();
            return false;
        }
        register_verifyLayout.setErrorEnabled(false);
        return true;
    }
    /**
     * 检查输入的密码是否为空、格式是否正确
     *
     * @return
     */
    public boolean isPasswordValid() {
        String pwd = passwordEdit.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)|!RegularUtils.isPasswordValid(pwd)) {
            register_passwordLayout.setErrorEnabled(true);
            register_passwordLayout.setError(getResources().getString(R.string.error_pwd));
            passwordEdit.requestFocus();
            return false;
        }
        register_passwordLayout.setErrorEnabled(false);
        return true;
    }
    public boolean isCPasswordValid() {
        String cpwd = cpasswordEdit.getText().toString().trim();
        if (TextUtils.isEmpty(cpwd)|(!cpasswordEdit.getText().toString().equals(passwordEdit.getText().toString()))) {
            register_cpasswordLayout.setErrorEnabled(true);
            register_cpasswordLayout.setError(getResources().getString(R.string.error_cpwd));
            cpasswordEdit.requestFocus();
            return false;
        }
        register_cpasswordLayout.setErrorEnabled(false);
        return true;
    }
    /*
    public boolean isEmailValid() {
        String email = emailEdit.getText().toString().trim();
        if (TextUtils.isEmpty(email)|!RegularUtils.isEmail(email)) {
            register_emailLayout.setErrorEnabled(true);
            register_emailLayout.setError(getResources().getString(R.string.error_email));
            emailEdit.requestFocus();
            return false;
        }
        register_emailLayout.setErrorEnabled(false);
        return true;
    }*/


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
                case R.id.register_phoneEdit:
                    isPhoneValid();
                    break;
                case R.id.register_verifyEdit:
                    isVerifyValid();
                    break;
                case R.id.register_passwordEdit:
                    isPasswordValid();
                    break;
                case R.id.register_cpasswordEdit:
                    isCPasswordValid();
                    break;
                //case R.id.register_emailEdit:
                //    isEmailValid();
                //    break;
            }
        }
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



    //输入合法性检测 合法返回true 不合法返回false
    private boolean inputValidityCheck(){
        //String email=emailEdit.getText().toString();
        String password=passwordEdit.getText().toString();
        String cpassword=cpasswordEdit.getText().toString();
        String phone=phoneEdit.getText().toString();
        String verify=verifyEdit.getText().toString();

        //if(email.equals("")){
           // Toast.makeText(RegisterActivity.this,"邮箱不能为空",Toast.LENGTH_SHORT).show();
            //return false;
        //}else
        if(password.equals("")){
            Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }else if(cpassword.equals("")){
            Toast.makeText(RegisterActivity.this,"确认密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }else if(phone.equals("")){
            Toast.makeText(RegisterActivity.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }else if(verify.equals("")){
            Toast.makeText(RegisterActivity.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!password.equals(cpassword)){
            Toast.makeText(RegisterActivity.this,"密码与确认密码不同，请重新输入",Toast.LENGTH_SHORT).show();
            return false;
        }

        String regex="1\\d{10,10}";
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(phone);
        if(phone.length()!=11||!matcher.matches()){
            Toast.makeText(RegisterActivity.this,"手机格式错误",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    //按钮初始化
    private void initButton(){
        verifyButton=(Button)findViewById(R.id.register_verifyButton);
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=phoneEdit.getText().toString();
                if(phone.equals("")){
                    Toast.makeText(RegisterActivity.this,"请填写手机号",Toast.LENGTH_SHORT).show();
                }else if(!isPhoneValid())
                {
                    Toast.makeText(RegisterActivity.this,"手机号格式错误",Toast.LENGTH_SHORT).show();
                }
                else{
                    ServerConnector.getInstance().sendRegisterShortMsg( phone, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this,"网络异常，发送失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String resp =response.body().string();
                            //Log.e("RES", resp);
                            //Log.e("RES", response.toString());
                            //Log.e("RES", String.valueOf(response.code()));
                            //Log.e("RES", "1");
                            JSONObject jsonObject= null;
                            try {
                                jsonObject = new JSONObject(resp);
                                int code =response.code();
                                if(code!=201){
                                    final String message=jsonObject.getString("phone");
                                    //Log.e("RES", "2");
                                    runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_SHORT).show();
                                            }
                                    });
                                }else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //Log.e("RES", "3");
                                            Toast.makeText(RegisterActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
                                            verifyButton.setEnabled(false);
                                            verifyButton.setBackgroundColor(Color.GRAY);
                                            verifyButton.setText("60s");
                                            timer.start();
                                        }
                                    });
                                }
                                //Log.e("RES", "4");
                            } catch (JSONException e) {
                                //Log.e("RES", "5");
                                e.printStackTrace();
                                //Log.e("RES", "6");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Toast.makeText(RegisterActivity.this,"发送异常",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    });
                    //Log.e("RES", "7");
                }
            }

        });
        //Log.e("RES", "8");

        Button registerButton=(Button)findViewById(R.id.register_registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputValidityCheck()){
                    final ProgressDialog pd=new ProgressDialog(RegisterActivity.this);
                    pd.setTitle("注册");
                    pd.setMessage("正在注册，请稍候...");
                    pd.setCancelable(false);
                    pd.show();

                    /*ServerConnector.getInstance().checkVarify(phoneEdit.getText().toString(),
                            verifyEdit.getText().toString(), new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            pd.dismiss();
                                            Toast.makeText(RegisterActivity.this,"网络异常，请检查网络设置",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String resp=response.body().string();
                                    JSONObject jsonObject= null;
                                    try {
                                        jsonObject = new JSONObject(resp);
                                        int code =response.code();
                                        if(code!=201){
                                            final String message=jsonObject.getString("username");
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    pd.dismiss();
                                                    Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }else{*/
                                            ServerConnector.getInstance().sendRegisterMsg(phoneEdit.getText().toString(),
                                                    passwordEdit.getText().toString(),verifyEdit.getText().toString(),new Callback() {
                                                        @Override
                                                        public void onFailure(Call call, IOException e) {
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    pd.dismiss();
                                                                    Toast.makeText(RegisterActivity.this,"网络异常，请检查网络设置",
                                                                            Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }

                                                        @Override
                                                        public void onResponse(Call call, Response response) throws IOException {
                                                            String responseData =response.body().string();
                                                            //Log.e("REGISTER", response.toString());
                                                            //Log.e("REGISTER", responseData);
                                                            //Log.e("REGISTER", String.valueOf(response.code()));
                                                            try{
                                                                //Log.e("REGISTER", "1");
                                                                final JSONObject jsonObject=new JSONObject(responseData);
                                                                int code =response.code();
                                                                //Log.e("REGISTER",  String.valueOf(code));
                                                                //Log.e("REGISTER", "2");
                                                                if(code!=201){
                                                                    final String message=jsonObject.getString("username");
                                                                    //Log.e("REGISTER", "failed");
                                                                    //Log.e("REGISTER", message);

                                                                    runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            pd.dismiss();
                                                                            Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                }else{
                                                                    runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            Log.e("REGISTER", "success");
                                                                            pd.dismiss();
                                                                            /*double grade= 0;
                                                                            try {
                                                                                String userString=jsonObject.getString("user");
                                                                                JSONObject userObject=new JSONObject(userString);
                                                                                grade = userObject.getDouble("grade");
                                                                            } catch (JSONException e) {
                                                                                e.printStackTrace();
                                                                            }*/
                                                                            User user= UserLib.getInstance().getUser();
                                                                            user.setUsername(phoneEdit.getText().toString());
                                                                            user.setPassword(passwordEdit.getText().toString());
                                                                            //user.setGrade(grade);
                                                                            //user.setPhone(phoneEdit.getText().toString());
                                                                            //user.setSpwd(null);
                                                                            //user.setSid(null);

                                                                            showMessage(getString(R.string.register_success));
                                                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                                            saveUPInFile(phoneEdit.getText().toString(),
                                                                                    passwordEdit.getText().toString());
                                                                            ActivityCollector.finishAll();
                                                                            startActivity(intent);
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                            catch(JSONException e){
                                                                e.printStackTrace();
                                                                //Log.e("REGISTER", "uochang");
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        pd.dismiss();
                                                                        Toast.makeText(RegisterActivity.this,"注册异常",Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });

                                                            }
                                                        }
                                                    });
                                       /* }
                                    }
                                        catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(RegisterActivity.this,"注册异常",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });*/
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initVariable();
        initToolbar();
        initTimer();
        initButton();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

