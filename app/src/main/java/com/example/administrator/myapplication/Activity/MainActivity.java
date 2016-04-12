package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myapplication.ActivityCollector.ActivityCollector;
import com.example.administrator.myapplication.Notification.WeatherNoti;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Search.SearchCity;

import com.example.administrator.myapplication.Service.UpdataService;
import com.example.administrator.myapplication.Setting.CustomDialog;
import com.example.administrator.myapplication.Setting.Setting;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements  OnClickListener {

    //当天天气
    @Bind(R.id.prov)
    TextView prov;
    @Bind(R.id.city)
    TextView city;
    @Bind(R.id.weatherdate)
    TextView weatherdate;
    @Bind(R.id.dayweather)
    TextView dayweather;
    @Bind(R.id.max)
    TextView max;
    @Bind(R.id.pm25)
    TextView pm25;
    @Bind(R.id.pm)
    TextView pm;
    @Bind(R.id.wind)
    TextView wind;
    @Bind(R.id.windspace)
    TextView windspace;
    //   第一天到第五天
    @Bind(R.id.first_data)
    TextView firstData;
    @Bind(R.id.first_max_weather)
    TextView firstMaxWeather;
    @Bind(R.id.first_min_weather)
    TextView firstMinWeather;
    @Bind(R.id.first_max)
    TextView firstMax;
    @Bind(R.id.first_min)
    TextView firstMin;
    @Bind(R.id.second_data)
    TextView secondData;
    @Bind(R.id.second_max_weather)
    TextView secondMaxWeather;
    @Bind(R.id.second_min_weather)
    TextView secondMinWeather;
    @Bind(R.id.second_max)
    TextView secondMax;
    @Bind(R.id.second_min)
    TextView secondMin;
    @Bind(R.id.third_data)
    TextView thirdData;
    @Bind(R.id.third_max_weather)
    TextView thirdMaxWeather;
    @Bind(R.id.third_min_weather)
    TextView thirdMinWeather;
    @Bind(R.id.third_max)
    TextView thirdMax;
    @Bind(R.id.third_min)
    TextView thirdMin;
    @Bind(R.id.fourth_data)
    TextView fourthData;
    @Bind(R.id.fourth_max_weather)
    TextView fourthMaxWeather;
    @Bind(R.id.fourth_min_weather)
    TextView fourthMinWeather;
    @Bind(R.id.fourth_max)
    TextView fourthMax;
    @Bind(R.id.fourth_min)
    TextView fourthMin;
    @Bind(R.id.fifth_data)
    TextView fifthData;
    @Bind(R.id.fifth_max_weather)
    TextView fifthMaxWeather;
    @Bind(R.id.fifth_min_weather)
    TextView fifthMinWeather;
    @Bind(R.id.fifth_max)
    TextView fifthMax;
    @Bind(R.id.fifth_min)
    TextView fifthMin;
    //    底部控件
    @Bind(R.id.switch_city)
    Button switchCity;
    @Bind(R.id.refresh_weather)
    Button refreshWeather;
    @Bind(R.id.setting)
    Button setting;
    //背景控件
    @Bind(R.id.weather_background)
    LinearLayout background;

    private boolean isExit = false;

//    private String url = "https://api.heweather.com/x3/weather?cityid=CN101010100&key=86d9d716bd5a4a0db7a1678f6b379e17";
    //个人认证key：86d9d716bd5a4a0db7a1678f6b379e17
//  https://api.heweather.com/x3/weather?cityid=CN101010100&key=86d9d716bd5a4a0db7a1678f6b379e17

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);
        setting.setOnClickListener(this);
        quer();
        ActivityCollector.addActivity(this);
    }


        public void quer(){
            String id =  getIntent().getStringExtra("cityid");
            SharedPreferences sharedPreferences = getSharedPreferences("cityId_fromclick",MODE_PRIVATE);
//            Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
            String weathercode =sharedPreferences.getString("cityName","");

            if (id == null){

                volley(weathercode);


            }else {
                volley(id);


            }

        }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){// 当keyCode等于退出事件值时

            exit();
            return false;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.switch_city:
                Intent intent = new Intent(this,SearchCity.class);
                startActivity(intent);

                break;
            case R.id.refresh_weather:
                Intent intent1 = new Intent(this, UpdataService.class);
                startService(intent1);
                break;
            case R.id.setting:
                Intent set = new Intent(this,Setting.class);
                startActivity(set);
                break;
            default:
                break;
        }
    }

    public void volley (String  weathercode) {
        String url = "https://api.heweather.com/x3/weather?cityid="+weathercode+"&key=86d9d716bd5a4a0db7a1678f6b379e17";
            RequestQueue mQueue = Volley.newRequestQueue(this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Gson(response.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", error.getMessage(), error);
                }
            });
            mQueue.add(jsonObjectRequest);
        }

    public void Gson(String json) {

        try {
            JSONObject jsonObject1 = new JSONObject(json);
            JSONArray HeWeather = jsonObject1.getJSONArray("HeWeather data service 3.0");
            for (int i = 0; i < HeWeather.length(); i++) {
                JSONObject jsonObject = (JSONObject) HeWeather.get(i);
                //请求成功
                String status = jsonObject.getString("status");
                Log.d("请求成功", "$$$$$$$$$$$$$$$$$$$$$$$$$$$");


                //空气质量模块 ok
//                JSONObject api = jsonObject.getJSONObject("aqi");
//                JSONObject City = api.getJSONObject("city");
//                String pm25 = City.getString("pm25");//pm2.5z指数
//                String qlty = City.getString("qlty");//空气指数
//                Log.d("空气质量模块", "###############################");
//                Log.d("pm2.5z指数", pm25);
//                Log.d("空气指数", qlty);


                //当天天气模块  ok
                JSONArray hourly_forecast = jsonObject.getJSONArray("hourly_forecast");
                String date = hourly_forecast.getJSONObject(0).getString("date");//当天时间和更新时间
                String hum = hourly_forecast.getJSONObject(0).getString("hum");//当天湿度
                String pop = hourly_forecast.getJSONObject(0).getString("pop");//当天降水概率
                String pres = hourly_forecast.getJSONObject(0).getString("pres");//当天气压
                String tmp = hourly_forecast.getJSONObject(0).getString("tmp");//当天温度
                Log.d("当天天气模块", "###############################");
                Log.d("当天时间和更新时间", date);
                Log.d("当天湿度", hum);
                Log.d("当天降水概率", pop);
                Log.d("当天气压", pres);
                Log.d("当天温度", tmp);


//         当天风力模块 ok
                JSONObject wind = hourly_forecast.getJSONObject(0).getJSONObject("wind");
                String deg = wind.getString("deg");//风向
                String dir = wind.getString("dir");//风向
                String sc = wind.getString("sc");//风力等级
                String spd = wind.getString("spd");//风速
                Log.d("当天风力模块", "###############################");
                Log.d("风向", deg);
                Log.d("风向", dir);
                Log.d("风力等级", sc);
                Log.d("风速", spd);


                //基础信息模块  ok
                JSONObject basic = jsonObject.getJSONObject("basic");
                JSONObject update = basic.getJSONObject("update");
                String loc = update.getString("loc");//城市更新时间

                String city = basic.getString("city");//城市
                String cnty = basic.getString("cnty");//省份
                String id = basic.getString("id");//城市代码
                String lat = basic.getString("lat");//城市纬度
                String lon = basic.getString("lon");//城市经度
                Log.d("基础信息模块", "###############################");
                Log.d("更新时间", loc);
                Log.d("城市", city);
                Log.d("省份", cnty);
                Log.d("城市代码", id);
                Log.d("城市纬度", lat);
                Log.d("城市经度", lon);


                //实况天气 ok
                JSONObject now = jsonObject.getJSONObject("now");
                JSONObject cond = now.getJSONObject("cond");
                String code = cond.getString("code");//天气代码
                String txt = cond.getString("txt");//天气描述


                String flWind = now.getString("fl");//体感温度
                String humWind = now.getString("hum");//湿度
                String pcpnWind = now.getString("pcpn");//降雨量
                String presWind = now.getString("pres");//气压
                String tmpWind = now.getString("tmp");//当前温度
                String visWind = now.getString("vis");//能见度

                JSONObject windNow = now.getJSONObject("wind");
                String degWind = windNow.getString("deg");//风向角度
                String dirWind = windNow.getString("dir");//风向方向
                String scWind = windNow.getString("sc");//风力等级
                String spdWind = windNow.getString("spd");//风速
                Log.d("实况天气", "###############################");
                Log.d("天气代码", code);
                Log.d("天气描述", txt);
                Log.d("体感温度", flWind);
                Log.d("湿度", humWind);
                Log.d("降雨量", pcpnWind);
                Log.d("气压", presWind);
                Log.d("当前温度", tmpWind);
                Log.d("能见度", visWind);
                Log.d("风向角度", degWind);
                Log.d("风向方向", dirWind);
                Log.d("风力等级", scWind);
                Log.d("风速", spdWind);


                //生活建议模块 ok
                JSONObject suggestion = jsonObject.getJSONObject("suggestion");
                JSONObject index0 = suggestion.getJSONObject("drsg");//穿衣
                JSONObject index1 = suggestion.getJSONObject("sport");//紫外线
                JSONObject index2 = suggestion.getJSONObject("cw");//洗车
                JSONObject index3 = suggestion.getJSONObject("trav");//旅游
                JSONObject index4 = suggestion.getJSONObject("flu");//感冒
                JSONObject index5 = suggestion.getJSONObject("sport");//运动


                String drsg_brf = index0.getString("brf");//
                String drsg_txt = index0.getString("txt");//
                String uv_brf = index1.getString("brf");//
                String uv_txt = index1.getString("txt");//
                String cw_brf = index2.getString("brf");//
                String cw_txt = index2.getString("txt");//
                String trav_brf = index3.getString("brf");//
                String trav_txt = index3.getString("txt");//
                String flu_brf = index4.getString("brf");//
                String flu_txt = index4.getString("txt");//
                String sport_brf = index5.getString("brf");//
                String sport_txt = index5.getString("txt");//
                Log.d("生活信息", "###############################");

                Log.d("穿衣建议", drsg_brf);
                Log.d("穿衣建议", drsg_txt);
                Log.d("紫外线建议", uv_brf);
                Log.d("紫外线建议", uv_txt);
                Log.d("洗车建议", cw_brf);
                Log.d("洗车建议", cw_txt);


                //未来五天天气模块
                JSONArray daily_forecast = jsonObject.getJSONArray("daily_forecast");

//         未来三天天气的第一天
                JSONObject condfirst = daily_forecast.getJSONObject(1).getJSONObject("cond");
                String datefirst = daily_forecast.getJSONObject(1).getString("date");//当天时间和更新时间
                String txt_dfirst = condfirst.getString("txt_d");//白天天气
                String txt_nfirst = condfirst.getString("txt_n");//夜晚天气
                Log.d("未来三天天气的第一天", "###############################");
                Log.d("当天时间和更新时间", datefirst);
                Log.d("白天天气", txt_dfirst);
                Log.d("夜晚天气", txt_nfirst);

                JSONObject tmpfirst = daily_forecast.getJSONObject(1).getJSONObject("tmp");
                String maxfirst = tmpfirst.getString("max");//最高温度
                String minfirst = tmpfirst.getString("min");//最低温度
                Log.d("最高温度", maxfirst);
                Log.d("最低温度", minfirst);

                //未来三天天气的第二天

                JSONObject condsecond = daily_forecast.getJSONObject(2).getJSONObject("cond");
                String datesecond = daily_forecast.getJSONObject(2).getString("date");//当天时间和更新时间
                String txt_dsecond = condsecond.getString("txt_d");//白天天气
                String txt_nsecond = condsecond.getString("txt_n");//夜晚天气
                Log.d("未来三天天气的第二天", "###############################");
                Log.d("当天时间和更新时间", datesecond);
                Log.d("白天天气", txt_dsecond);
                Log.d("夜晚天气", txt_nsecond);

                JSONObject tmpsecond = daily_forecast.getJSONObject(2).getJSONObject("tmp");
                String maxsecond = tmpsecond.getString("max");//最高温度
                String minsecond = tmpsecond.getString("min");//夜晚天气

                Log.d("最高温度", maxsecond);
                Log.d("最低温度", minsecond);

                //未来三天天气的第三天

                JSONObject condthird = daily_forecast.getJSONObject(3).getJSONObject("cond");
                String datethird = daily_forecast.getJSONObject(3).getString("date");//当天时间和更新时间
                String txt_dthird = condthird.getString("txt_d");//白天天气
                String txt_nthird = condthird.getString("txt_n");//夜晚天气
                Log.d("未来三天天气的第三天", "###############################");
                Log.d("当天时间和更新时间", datethird);
                Log.d("白天天气", txt_dthird);
                Log.d("夜晚天气", txt_nthird);

                JSONObject tmpthird = daily_forecast.getJSONObject(3).getJSONObject("tmp");
                String maxthird = tmpthird.getString("max");//白天天气
                String minthird = tmpthird.getString("min");//夜晚天气

                Log.d("最高温度", maxthird);
                Log.d("最低温度", minthird);
                //未来三天天气的第四天

                JSONObject condfourth = daily_forecast.getJSONObject(4).getJSONObject("cond");
                String datefourth = daily_forecast.getJSONObject(4).getString("date");//当天时间和更新时间
                String txt_dfourth = condfourth.getString("txt_d");//白天天气
                String txt_nfourth = condfourth.getString("txt_n");//夜晚天气
                Log.d("未来三天天气的第四天", "###############################");
                Log.d("当天时间和更新时间", datefourth);
                Log.d("白天天气", txt_dfourth);
                Log.d("夜晚天气", txt_nfourth);

                JSONObject tmpfourth = daily_forecast.getJSONObject(4).getJSONObject("tmp");
                String maxfourth = tmpfourth.getString("max");//白天天气
                String minfourth = tmpfourth.getString("min");//夜晚天气
                Log.d("最高温度", maxfourth);
                Log.d("最低温度", minfourth);

                //未来三天天气的第五天

                JSONObject condfifth = daily_forecast.getJSONObject(5).getJSONObject("cond");
                String datefifth = daily_forecast.getJSONObject(5).getString("date");//当天时间和更新时间
                String txt_dfifth = condfifth.getString("txt_d");//白天天气
                String txt_nfifth = condfifth.getString("txt_n");//夜晚天气
                Log.d("未来三天天气的第五天", "###############################");
                Log.d("当天时间和更新时间", datefifth);
                Log.d("白天天气", txt_dfifth);
                Log.d("夜晚天气", txt_nfifth);

                JSONObject tmpfifth = daily_forecast.getJSONObject(5).getJSONObject("tmp");
                String maxfifth = tmpfifth.getString("max");//白天天气
                String minfifth = tmpfifth.getString("min");//夜晚天气
                Log.d("最高温度", maxfifth);
                Log.d("最低温度", minfifth);


                //保存天气pm2.5和基本城市信息
                saveWeather(hum, pop, city, cnty, loc,id);
                //保存当天天气
                saveWeaterNow(txt, tmpWind, flWind, humWind, scWind, dirWind, spdWind);
                //保存生活信息
                saveWeatherSuggestion(
                        drsg_brf, drsg_txt, uv_brf, uv_txt, cw_brf, cw_txt,
                        trav_brf, trav_txt, flu_brf, flu_txt, sport_brf, sport_txt);
                //保存未来五天数据
                saveWeatherThreeDay(
                        datefirst, txt_dfirst, txt_nfirst, maxfirst, minfirst,
                        datesecond, txt_dsecond, txt_nsecond, maxsecond, minsecond,
                        datethird, txt_dthird, txt_nthird, maxthird, minthird,
                        datefourth, txt_dfourth, txt_nfourth, maxfourth, minfourth,
                        datefifth, txt_dfifth, txt_nfifth, maxfifth, minthird);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //保存未来五天数据
    public void saveWeatherThreeDay(
            String datefirst, String txt_dfirst, String txt_nfirst, String maxfirst, String minfirst,
            String datesecond, String txt_dsecond, String txt_nsecond, String maxsecond, String minsecond,
            String datethird, String txt_dthird, String txt_nthird, String maxthird, String minthird,
            String datefourth, String txt_dfourth, String txt_nfourth, String maxfourth, String minfourth,
            String datefifth, String txt_dfifth, String txt_nfifth, String maxfifth, String minfifth


    ) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        //未来三天天气的第一天
        editor.putString("datefirst", datefirst);
        editor.putString("txt_dfirst", txt_dfirst);
        editor.putString("txt_nfirst", txt_nfirst);
        editor.putString("maxfirst", maxfirst);
        editor.putString("minfirst", minfirst);
        //未来三天天气的第二天
        editor.putString("datesecond", datesecond);
        editor.putString("txt_dsecond", txt_dsecond);
        editor.putString("txt_nsecond", txt_nsecond);
        editor.putString("maxsecond", maxsecond);
        editor.putString("minsecond", minsecond);
        //未来三天天气的第三天
        editor.putString("datethird", datethird);
        editor.putString("txt_dthird", txt_dthird);
        editor.putString("txt_nthird", txt_nthird);
        editor.putString("maxthird", maxthird);
        editor.putString("minthird", minthird);
        //未来三天天气的第四天
        editor.putString("datefourth", datefourth);
        editor.putString("txt_dfourth", txt_dfourth);
        editor.putString("txt_nfourth", txt_nfourth);
        editor.putString("maxfourth", maxfourth);
        editor.putString("minfourth", minfourth);
        //未来三天天气的第五天
        editor.putString("datefifth", datefifth);
        editor.putString("txt_dfifth", txt_dfifth);
        editor.putString("txt_nfifth", txt_nfifth);
        editor.putString("maxfifth", maxfifth);
        editor.putString("minfifth", minfifth);
        editor.commit();
        Log.d("保存未来五天数据", "////////////////////////////////////");
    }

    //保存生活建议
    public void saveWeatherSuggestion(
            String drsg_brf, String drsg_txt,
            String uv_brf, String uv_txt,
            String cw_brf, String cw_txt,
            String trav_brf, String trav_txt,
            String flu_brf, String flu_txt,
            String sport_brf, String sport_txt
    ) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        //穿衣指数
        editor.putString("drsg_brf", drsg_brf);
        editor.putString("drsg_txt", drsg_txt);
        //紫外线指数
        editor.putString("uv_brf", uv_brf);
        editor.putString("uv_txt", uv_txt);
        //洗车指数
        editor.putString("cw_brf", cw_brf);
        editor.putString("cw_txt", cw_txt);
        //旅游指数
        editor.putString("trav_brf", trav_brf);
        editor.putString("trav_txt", trav_txt);
        //感冒指数
        editor.putString("flu_brf", flu_brf);
        editor.putString("flu_txt", flu_txt);
        //运动指数
        editor.putString("sport_brf", sport_brf);
        editor.putString("sport_txt", sport_txt);
        editor.commit();
        Log.d("保存生活建议", "////////////////////////////////////");
    }

    //保存当天天气状况
    public void saveWeaterNow(
            String txt, String tmpWind, String flWind, String humWind,
                              String scWind, String dirWind, String spdWind) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString("txt", txt);
        editor.putString("tmpWind", tmpWind);//当前温度
        editor.putString("flWind", flWind);
        editor.putString("humWind", humWind);//湿度
        editor.putString("scWind", scWind);//风力等级
        editor.putString("dirWind", dirWind);//风向方向
        editor.putString("spdWind", spdWind);//风速
        editor.commit();
        Log.d("保存当天天气状况", "////////////////////////////////////");


    }
    //保存天气pm2.5和基本城市信息

    public void saveWeather(
            String hum, String pop, String city, String cnty, String loc,String id) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString("hum", hum);
        editor.putString("pop", pop);
        editor.putString("city", city);
        editor.putString("cnty", cnty);
        editor.putString("loc", loc);
        editor.putString("id", id);
        editor.commit();
        Log.d("保存天气pm2.5和基本城市信息", "////////////////////////////////////");
        showWeather();
    }

    public void showWeather() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        weatherdate.setText(sharedPreferences.getString("loc", ""));
        dayweather.setText(sharedPreferences.getString("txt", ""));
        max.setText(sharedPreferences.getString("tmpWind", "") + "℃");
        pm25.setText(sharedPreferences.getString("hum", ""));
        pm.setText(sharedPreferences.getString("pop", ""));
        wind.setText(sharedPreferences.getString("dirWind", ""));
        windspace.setText(sharedPreferences.getString("spdWind", ""));
        prov.setText(sharedPreferences.getString("cnty", ""));
        city.setText(sharedPreferences.getString("city", ""));
        //未来第一天
        firstData.setText(sharedPreferences.getString("datefirst", ""));
        firstMax.setText(sharedPreferences.getString("maxfirst", "") + "°");
        firstMin.setText(sharedPreferences.getString("minfirst", "") + "°");
        firstMaxWeather.setText(sharedPreferences.getString("txt_dfirst", ""));
        firstMinWeather.setText(sharedPreferences.getString("txt_nfirst", ""));
        //未来第二天
        secondData.setText(sharedPreferences.getString("datesecond", ""));
        secondMax.setText(sharedPreferences.getString("maxsecond", "") + "°");
        secondMin.setText(sharedPreferences.getString("minsecond", "") + "°");
        secondMaxWeather.setText(sharedPreferences.getString("txt_dsecond", ""));
        secondMinWeather.setText(sharedPreferences.getString("txt_nsecond", ""));
        //未来第三天
        thirdData.setText(sharedPreferences.getString("datethird", ""));
        thirdMax.setText(sharedPreferences.getString("maxthird", "") + "°");
        thirdMin.setText(sharedPreferences.getString("minthird", "") + "°");
        thirdMaxWeather.setText(sharedPreferences.getString("txt_dthird", ""));
        thirdMinWeather.setText(sharedPreferences.getString("txt_nthird", ""));
        //未来第四天
        fourthData.setText(sharedPreferences.getString("datefourth", ""));
        fourthMax.setText(sharedPreferences.getString("maxfourth", "") + "°");
        fourthMin.setText(sharedPreferences.getString("minfourth", "") + "°");
        fourthMaxWeather.setText(sharedPreferences.getString("txt_dfourth", ""));
        fourthMinWeather.setText(sharedPreferences.getString("txt_nfourth", ""));

        //未来第五天
        fifthData.setText(sharedPreferences.getString("datefifth", ""));
        fifthMax.setText(sharedPreferences.getString("maxfifth", "") + "°");
        fifthMin.setText(sharedPreferences.getString("minfifth", "") + "°");
        fifthMaxWeather.setText(sharedPreferences.getString("txt_dfifth", ""));
        fifthMinWeather.setText(sharedPreferences.getString("txt_nfifth", ""));

        showPicture();
        Intent serviceIntent = new Intent(this, WeatherNoti.class);
        startService(serviceIntent);

    }

//更换背景图片

    public void showPicture() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weather = sharedPreferences.getString("txt", "");
        if (weather.equals("晴") || weather.equals("少云")  ) {background.setBackgroundResource(R.drawable.qing);

        } else if (weather.equals("强阵雨") || weather.equals("雷阵雨") || weather.equals("强雷阵雨")
                || weather.equals("雷阵雨伴有冰雹") || weather.equals("大雨") || weather.equals("极端降雨")
                || weather.equals("暴雨") || weather.equals("大暴雨") || weather.equals("特大暴雨")
                ) {
            background.setBackground(ContextCompat.getDrawable(this, R.drawable.baoyu));
        } else if (weather.equals("大雪") || weather.equals("暴雪")
                ) {

            background.setBackground(ContextCompat.getDrawable(this, R.drawable.daxue));

        } else if (weather.equals("扬沙") || weather.equals("浮尘") || weather.equals("火山灰")
                || weather.equals("沙尘暴") || weather.equals("强沙尘暴")) {

            background.setBackground(ContextCompat.getDrawable(this, R.drawable.shachenbao));

        } else if ( weather.equals("阴")  ) {

            background.setBackground(ContextCompat.getDrawable(this, R.drawable.yintian));

        } else if (weather.equals("毛毛雨")|| weather.equals("小雨") || weather.equals("中雨")
                || weather.equals("冻雨") || weather.equals("阵雨")) {

            background.setBackground(ContextCompat.getDrawable(this, R.drawable.xiaoyu));

        }else if (weather.equals("小雪") || weather.equals("中雪") || weather.equals("雨夹雪")
                || weather.equals("雨雪天气") || weather.equals("阵雨夹雪") || weather.equals("阵雪")) {

            background.setBackground(ContextCompat.getDrawable(this, R.drawable.xiaoxue));

        }
        else if (weather.equals("薄雾") || weather.equals("雾") || weather.equals("霾")) {

            background.setBackground(ContextCompat.getDrawable(this, R.drawable.wu));

        }
        else if (  weather.equals("多云")|| weather.equals("晴间多云") ) {

            background.setBackground(ContextCompat.getDrawable(this, R.drawable.duoyun));

        }

    }

    private void exit() {
        if (isExit) {
            // ACTION_MAIN with category CATEGORY_HOME 启动主屏幕
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);// 使虚拟机停止运行并退出程序
        }
        else {
            isExit = true;
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 3000);// 3秒后发送消息
        }
    }

    Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)//处理消息
        {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }


}