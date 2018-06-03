package com.example.iphotos.fragment;

import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iphotos.R;

import java.io.File;
import java.util.ArrayList;


import com.example.iphotos.adapter.ImageAdapter;
import com.example.iphotos.entity.FileItem;
import com.example.iphotos.entity.ImageTime;
import com.trustyapp.gridheaders.TrustyGridGridView;

public class TimeMapPhotosFragment2 extends Fragment  implements SwipeRefreshLayout.OnRefreshListener {


        private TrustyGridGridView gvImage;
        private ImageAdapter adapter;
        private ArrayList<FileItem> fileInfo = new ArrayList<>();
        private SwipeRefreshLayout swipeRefreshLayout;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.activity_main, container, false);
            //initView(view);
            //initData();
            return view;
        }


        private void initView(View view) {
           // gvImage = (TrustyGridGridView) view.findViewById(R.id.gv_image);
            adapter = new ImageAdapter(getActivity(), fileInfo);
            gvImage.setAdapter(adapter);
            //swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
            swipeRefreshLayout.setOnRefreshListener(this);
        }


        @Override
        public void onRefresh() {
            //下拉刷新
        }
}
