package com.example.iphotos.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iphotos.R;


public class LockActivity extends AppCompatActivity {

    private  RadioGroup lockRadioGroup;
    private  RadioButton open;
    private  RadioButton close;
    private Button lock_change;
    private Button lock_return;

    private void initButton(){
        lock_change = (Button) findViewById(R.id.lock_changeButton);
        lock_return = (Button) findViewById(R.id.lock_returnButton);
        lock_change.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Toast.makeText(getApplicationContext(),"修改手势密码",Toast.LENGTH_LONG).show();
               }
           }
        );
        lock_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //菜单栏初始化
    private void initToolbar(){
        Toolbar toolbar=(Toolbar)findViewById(R.id.lock_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private  void initRadio(){

        lockRadioGroup = (RadioGroup)findViewById(R.id.lock_set_radio);
        open = (RadioButton)findViewById(R.id.lock_set_open);
        close = (RadioButton)findViewById(R.id.lock_set_close);
        close.setChecked(true);
        lockRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String msg = "";
                if(open.getId()==checkedId){
                    msg = "已打开软件锁，请及时设置手势密码";
                }
                if(close.getId()==checkedId){
                    msg = "已关闭软件锁";
                }
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        initToolbar();
        initRadio();
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
