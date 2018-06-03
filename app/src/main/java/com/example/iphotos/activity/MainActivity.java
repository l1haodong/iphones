package com.example.iphotos.activity;

/**
 * time_map_photos
 * sorted_photos
 * local_photos
 * photos_assistant
 *
 * */

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
//import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iphotos.R;
import com.example.iphotos.adapter.SlidingMenuListAdapter;
import com.example.iphotos.entity.Menu;
import com.example.iphotos.fragment.LocalPhotosFragment;
import com.example.iphotos.fragment.PhotosAssistantFragment;
import com.example.iphotos.fragment.SortedPhotosFragment;
import com.example.iphotos.fragment.TimeMapPhotosFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout; //活动主布局
    private TextView toolbarText;  //菜单栏TextView
    //底部菜单栏 子项布局 图片 文字
    private LinearLayout time_map_photos;
    private LinearLayout sorted_photos;
    private LinearLayout local_photos;
    private LinearLayout photos_assistant;

    private ImageView time_map_photos_img;
    private ImageView sorted_photos_img;
    private ImageView local_photos_img;
    private ImageView photos_assistant_img;

    private TextView time_map_photos_text;
    private TextView sorted_photos_text;
    private TextView local_photos_text;
    private TextView photos_assistant_text;

    private List<Menu>menuList=new ArrayList<>();

    //ViewPager显示内容
    private ViewPager viewPager;
    private int page=0;     //ViewPager页码
    private List<Fragment> fragments=new ArrayList<>();

    private static final int REQUEST_PERSONAL_INFO=0;

    private static final int RESULT_UPDATE_SID=2;
    private static final int RESULT_UPDATE_PHONE=3;
    private static final int RESULT_UPDATE_ALL=4;


    //变量初始化
    private void initVariable() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawerLayout);
        toolbarText = (TextView) findViewById(R.id.main_toolbarText);

        time_map_photos=(LinearLayout)findViewById(R.id.bottom_integrated);
        sorted_photos=(LinearLayout)findViewById(R.id.bottom_event);
        local_photos=(LinearLayout)findViewById(R.id.bottom_target);
        photos_assistant=(LinearLayout)findViewById(R.id.bottom_analysis);

        time_map_photos_img=(ImageView)findViewById(R.id.bottom_integrated_img);
        sorted_photos_img=(ImageView)findViewById(R.id.bottom_event_img);
        local_photos_img=(ImageView)findViewById(R.id.bottom_target_img);
        photos_assistant_img=(ImageView)findViewById(R.id.bottom_analysis_img);

        time_map_photos_text=(TextView)findViewById(R.id.bottom_integrated_text);
        sorted_photos_text=(TextView)findViewById(R.id.bottom_event_text);
        local_photos_text=(TextView)findViewById(R.id.bottom_target_text);
        photos_assistant_text=(TextView)findViewById(R.id.bottom_analysis_text);

        viewPager=(ViewPager)findViewById(R.id.main_contentView);


    }

    //菜单栏初始化
    private void initToolbar(){
        Toolbar toolbar=(Toolbar)findViewById(R.id.main_toolbar);
        toolbar.setTitle("");
        toolbarText.setText("时光地图");
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }
    //滑动菜单菜单列表数据初始化
    private void initMenuData(){
        //menuList.add(new Menu("个人信息",R.drawable.sliding_menu_mygrade));
        menuList.add(new Menu("云相册",R.drawable.sliding_menu_personal_info));
        menuList.add(new Menu("相册锁",R.drawable.sliding_menu_setting));
        menuList.add(new Menu("隐私空间",R.drawable.sliding_menu_personal_info));
        menuList.add(new Menu("回收箱",R.drawable.sliding_menu_setting));
        menuList.add(new Menu("设置",R.drawable.sliding_menu_setting));
        menuList.add(new Menu("退出登录",R.drawable.sliding_menu_logout));
        menuList.add(new Menu("关闭程序",R.drawable.sliding_menu_exit));
    }

    //滑动菜单初始化
    private void initSlidingMenu(){
        initMenuData();
        final ListView menu=(ListView)findViewById(R.id.slidingMenu_menuList);
        SlidingMenuListAdapter adapter=new SlidingMenuListAdapter(MainActivity.this,R.layout.sliding_menulist_item,menuList);
        menu.setAdapter(adapter);
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        //Intent intent_0=new Intent(MainActivity.this,PersonalInfoActivity.class);
                        //startActivityForResult(intent_0,REQUEST_PERSONAL_INFO);
                        break;
                    case 1:
                        //Intent intent_1 = new Intent(MainActivity.this, CreditDetailActivity.class);
                        //startActivity(intent_1);
                        break;
                    case 2:
                        //Intent intent_2=new Intent(MainActivity.this,SettingActivity.class);
                        //startActivity(intent_2);
                        break;
                    case 3:
                        //exitLogin();
                        break;
                    case 4:
                        //finish();
                        break;
                    case 5:
                        exitLogin();
                        break;
                    case 6:
                        //exitLogin();
                        break;
                    default:
                        break;
                }
            }
        });

        //过滤Banner点击事件
        RelativeLayout headerLayout=(RelativeLayout)findViewById(R.id.slidingMenu_headerLayout);
        LinearLayout occupyLayout_1=(LinearLayout)findViewById(R.id.slidingMenu_bottom_occupyLayout_1);
        LinearLayout occupyLayout_2=(LinearLayout)findViewById(R.id.slidingMenu_bottom_occupyLayout_2);
        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        occupyLayout_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        occupyLayout_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    //退出登录
    private void exitLogin(){
        //清除登录信息文件
        SharedPreferences.Editor editor=getSharedPreferences("data_up",0).edit();
        editor.clear();
        editor.apply();

        //返回登录界面
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariable();
        initToolbar();
        initSlidingMenu();
        initBottomMenu();
        initViewPager();
        //initUser();

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //Log.e("SC", "FileDD VVVD is null.");
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //logonValidate(phone1,password1);
                    //Log.e("SC", "YES");
                } else {
                    //Log.e("SC", "You denied the permission");
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //按下返回键
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.cloud:
                //intent=new Intent(MainActivity.this,RobotActivity.class);
                //startActivity(intent);
                Toast.makeText(getApplicationContext(),"单击完成",Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    //底部菜单栏初始化
    private void initBottomMenu(){
        time_map_photos_img.setImageResource(R.drawable.integrated_selected);
        time_map_photos_text.setTextColor(Color.parseColor("#1C86EE"));

        time_map_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(page!=0){
                    viewPager.setCurrentItem(0);
                }
            }
        });

        sorted_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(page!=1){
                    viewPager.setCurrentItem(1);
                }
            }
        });

        local_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(page!=2){
                    viewPager.setCurrentItem(2);
                }
            }
        });

        photos_assistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(page!=3){
                    viewPager.setCurrentItem(3);
                }
            }
        });
    }

    //重置底部菜单为初始样式
    private void resetBottomMenuStyle(){
        if(page==0){
            time_map_photos_img.setImageResource(R.drawable.integrated_normal);
            time_map_photos_text.setTextColor(Color.parseColor("#B5B5B5"));
        }
        else if(page==1){
            sorted_photos_img.setImageResource(R.drawable.event_normal);
            sorted_photos_text.setTextColor(Color.parseColor("#B5B5B5"));
        }
        else if(page==2){
            local_photos_img.setImageResource(R.drawable.target_normal);
            local_photos_text.setTextColor(Color.parseColor("#B5B5B5"));
        }
        else{
            photos_assistant_img.setImageResource(R.drawable.analysis_normal);
            photos_assistant_text.setTextColor(Color.parseColor("#B5B5B5"));
        }
    }

    //根据page设置底部菜单样式
    private void setBottomMenuStyle(){
        if(page==0){
            time_map_photos_img.setImageResource(R.drawable.integrated_selected);
            time_map_photos_text.setTextColor(Color.parseColor("#1C86EE"));
            toolbarText.setText("时光地图");
        }
        else if(page==1){
            sorted_photos_img.setImageResource(R.drawable.event_selected);
            sorted_photos_text.setTextColor(Color.parseColor("#1C86EE"));
            toolbarText.setText("分类相册");
        }
        else if(page==2){
            local_photos_img.setImageResource(R.drawable.target_selected);
            local_photos_text.setTextColor(Color.parseColor("#1C86EE"));
            toolbarText.setText("本地相册");
        }
        else{
            photos_assistant_img.setImageResource(R.drawable.analysis_selected);
            photos_assistant_text.setTextColor(Color.parseColor("#1C86EE"));
            toolbarText.setText("相册助手");
        }
    }

    //ViewPager初始化
    private void initViewPager(){
        fragments.add(new TimeMapPhotosFragment());
        fragments.add(new SortedPhotosFragment());
        fragments.add(new LocalPhotosFragment());
        fragments.add(new PhotosAssistantFragment());

        FragmentManager fragmentManager=getSupportFragmentManager();
        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                return  fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetBottomMenuStyle();
                page=position;
                setBottomMenuStyle();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
