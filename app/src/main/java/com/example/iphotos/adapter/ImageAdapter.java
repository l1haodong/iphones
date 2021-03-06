package com.example.iphotos.adapter;
/*
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.trustyapp.gridheaders.TrustyGridSimpleAdapter;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class ImageAdapter extends BaseAdapter implements TrustyGridSimpleAdapter {
    private Context mContext;

    private ArrayList<ImageTime> fileInfo;

    public ImageAdapter(Context mContext,ArrayList<ImageTime> fileInfo) {
        this.mContext = mContext;
        this.fileInfo =fileInfo;
    }

    public void setData(ArrayList<ImageTime> fileInfo){
        this.fileInfo = fileInfo;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (fileInfo!=null && fileInfo.size()>0){
            count = fileInfo.size();
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        ImageView ivImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext,R.layout.item_image,null);

            viewHolder.ivImage = (ImageView)convertView.findViewById(R.id.iv_image);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if (fileInfo!=null && fileInfo.size()>0) {

            File file = new File(fileInfo.get(position).getFilePath());
            Glide.with(mContext).load(file).asBitmap().dontAnimate().centerCrop()
                    .signature(new MediaStoreSignature("image/jpeg", file.lastModified(), 0))
                    .into(viewHolder.ivImage);
        }
        return convertView;
    }

    class HeaderViewHolder {
        public TextView tvTimeHeader;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup viewGroup) {

        HeaderViewHolder mHeadViewHolder = null;
        if (convertView==null){
            mHeadViewHolder = new HeaderViewHolder();
            convertView = View.inflate(mContext,R.layout.item_time_header,null);

            mHeadViewHolder.tvTimeHeader = (TextView) convertView.findViewById(R.id.tv_time_header);

            convertView.setTag(mHeadViewHolder);
        }else {
            mHeadViewHolder = (HeaderViewHolder)convertView.getTag();
        }

        mHeadViewHolder.tvTimeHeader.setText(fileInfo.get(position).getDate());

        return convertView;
    }

    @Override
    public long getHeaderId(int i) {
        // File file = new File(fileInfo.get(i).getFilePath());
        return getTimeId(fileInfo.get(i).getDate());
        //  return getTimeId(strToDateLong(file.lastModified()));
    }

    public long getTimeId(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date mDate = null;

        try {
            mDate = sdf.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        }
        return mDate.getTime();
    }

    public String strToDateLong(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(date);
    }

}  */


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.iphotos.R;
import com.example.iphotos.entity.FileItem;
import com.example.iphotos.tools.DirUtil;
import com.trustyapp.gridheaders.TrustyGridSimpleAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ImageAdapter extends BaseAdapter implements TrustyGridSimpleAdapter {
    private Context mContext;

    private ArrayList<FileItem> fileInfo;

    public ImageAdapter(Context mContext, ArrayList<FileItem> fileInfo) {
        this.mContext = mContext;
        this.fileInfo = fileInfo;
    }

    public void setData(ArrayList<FileItem> fileInfo) {
        this.fileInfo = fileInfo;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (fileInfo != null && fileInfo.size() > 0) {
            count = fileInfo.size();
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_image, null);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.iv_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (fileInfo != null && fileInfo.size() > 0) {
            FileItem item = fileInfo.get(position);
            Glide.with(mContext).load(item.getDownloadUrl()).into(viewHolder.ivImage);
        }
        return convertView;
    }


    @Override
    public View getHeaderView(int position, View convertView, ViewGroup viewGroup) {

        HeaderViewHolder mHeadViewHolder = null;
        if (convertView == null) {
            mHeadViewHolder = new HeaderViewHolder();
            convertView = View.inflate(mContext, R.layout.item_time_header, null);
            mHeadViewHolder.tvTimeHeader = (TextView) convertView.findViewById(R.id.tv_time_header);
            convertView.setTag(mHeadViewHolder);
        } else {
            mHeadViewHolder = (HeaderViewHolder) convertView.getTag();
        }
        mHeadViewHolder.tvTimeHeader.setText(DirUtil.getPhotoTime(fileInfo.get(position).getCtime()));
        return convertView;
    }

    @Override
    public long getHeaderId(int i) {
        return getTimeId(DirUtil.getPhotoTime(fileInfo.get(i).getCtime()));
    }

    public long getTimeId(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date mDate = null;

        try {
            mDate = sdf.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mDate.getTime();
    }


    class HeaderViewHolder {
        public TextView tvTimeHeader;
    }

    class ViewHolder {
        ImageView ivImage;
    }
}