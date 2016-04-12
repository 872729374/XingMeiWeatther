package com.example.administrator.myapplication.Setting;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/10.
 */
public class Setting extends Activity implements View.OnClickListener{


    @Bind(R.id.dress_but)
    Button dressBut;
    @Bind(R.id.uv_but)
    Button uvBut;
    @Bind(R.id.cw_but)
    Button cwBut;
    @Bind(R.id.trav_but)
    Button travBut;
    @Bind(R.id.flu_but)
    Button fluBut;
    @Bind(R.id.sport_but)
    Button sportBut;
    @Bind(R.id.suggest_btn)
    Button suggestBtn;
    @Bind(R.id.about_btn)
    Button aboutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting);
        ButterKnife.bind(this);
        dressBut.setOnClickListener(this);
        uvBut.setOnClickListener(this);
        cwBut.setOnClickListener(this);
        travBut.setOnClickListener(this);
        fluBut.setOnClickListener(this);
        sportBut.setOnClickListener(this);
        suggestBtn.setOnClickListener(this);
        aboutBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        switch (v.getId()) {

            case R.id.dress_but://穿衣
                String drsg_brf =sharedPreferences.getString("drsg_brf","");
                String drsg_txt =sharedPreferences.getString("drsg_txt","");

                showAlertDialog(drsg_brf,drsg_txt);
                break;
            case R.id.uv_but://紫外线
                String uv_brf =sharedPreferences.getString("uv_brf","");
                String uv_txt =sharedPreferences.getString("uv_txt","");
                showAlertDialog(uv_brf,uv_txt);
                break;
            case R.id.cw_but://洗车
                String cw_brf =sharedPreferences.getString("cw_brf","");
                String cw_txt =sharedPreferences.getString("cw_txt","");
                showAlertDialog(cw_brf,cw_txt);
                break;

            case R.id.trav_but://旅游
                String trav_brf =sharedPreferences.getString("trav_brf","");
                String trav_txt =sharedPreferences.getString("trav_txt","");
                showAlertDialog(trav_brf,trav_txt);
                break;
            case R.id.flu_but://感冒
                String flu_brf =sharedPreferences.getString("flu_brf","");
                String flu_txt =sharedPreferences.getString("flu_txt","");
                showAlertDialog(flu_brf,flu_txt);

                break;
            case R.id.sport_but://运动
                String sport_brf =sharedPreferences.getString("sport_brf","");
                String sport_txt =sharedPreferences.getString("sport_txt","");
                showAlertDialog(sport_brf,sport_txt);
                break;
            case R.id.suggest_btn:
                String a ="意见";
                String b ="有意见请发送872729374@qq.com";
                showAlertDialog(a,b);

                break;
            case R.id.about_btn:
                String c ="关于";
                String d ="天气覆盖国内2567个市县地区";
                showAlertDialog(c,d);
                break;

            default:
                break;
        }
    }



    public void showAlertDialog(String f,String d) {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage(d);
        builder.setTitle(f);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
            }
        });

        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();

    }
}