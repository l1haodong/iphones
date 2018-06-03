package com.example.iphotos.entity;

/**
 * Created by 李浩东 on 2018/5/1.
 */

public class FileItem {

    //创建的时间戳
    private long ctime;

    private String downloadUrl;



    public FileItem(long ctime, String downloadUrl) {
        this.ctime = ctime;
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }
}
