package com.example.iphotos.entity;

/**
 * Created by Administrator on 2017/2/23.
 */

public class User {

    private String username;  //用户名
    private String password;  //密码
    private String phone;  //手机号
    private String email;  //邮箱



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
