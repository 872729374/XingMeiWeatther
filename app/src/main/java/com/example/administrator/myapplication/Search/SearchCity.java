package com.example.administrator.myapplication.Search;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.myapplication.Activity.MainActivity;
import com.example.administrator.myapplication.R;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;




public class SearchCity extends Activity {
    private EditText address;
    public ListView listView;
    public Button search;
    private String url = "https://api.heweather.com/x3/citylist?search=allchina&key=86d9d716bd5a4a0db7a1678f6b379e17";
    CityDatabase cd = new CityDatabase(this, "CITY_LIST", null, 1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search);
        search = (Button) findViewById((R.id.search));
        address = (EditText) findViewById(R.id.addresss);
        listView = (ListView) findViewById(R.id.listView);

        new MyThread().start();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String st = address.getText().toString();
                searchCity(st);
            }
        });
    }
  class MyThread extends Thread{
      @Override
      public void run() {
          super.run();
          volley();

      }
  }


    public void volley() {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
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
        SQLiteDatabase db = cd.getWritableDatabase();
        db.beginTransaction();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray citylist = jsonObject.getJSONArray("city_info");
            for (int i = 0; i < citylist.length(); i++) {
                JSONObject citylist_http = (JSONObject) citylist.get(i);
                //取出name
                String cityHttp = citylist_http.getString("city");//城市
                String numHttp = citylist_http.getString("id");//代号
                db.execSQL("insert into CITY_LIST( city, num) values(?,?)", new String[]{cityHttp, numHttp});
                Log.d("1111111", "保存成功一条");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void AlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SearchCity.this);
        builder.setTitle("温馨提示");
        builder.setMessage("请输入中文城市名,中间不要有其他符号");
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();
    }


    protected  void searchCity(String city){
        CityDatabase cityDatabase = new CityDatabase(this,"CITY_LIST",null,1);
        SQLiteDatabase db = cityDatabase.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM CITY_LIST WHERE city = ?", new String[]{city.toString()});
        if(cursor.moveToFirst()){
//            String cityName_fromclick =cursor.getString(cursor.getColumnIndex("city"));
            String cityId_fromclick =cursor.getString(cursor.getColumnIndex("num"));
            SharedPreferences.Editor editor =getSharedPreferences("cityId_fromclick",MODE_PRIVATE).edit();
            editor.putString("cityName",cityId_fromclick);
            editor.commit();
            Intent intent = new Intent(SearchCity.this,MainActivity.class);
            intent.putExtra("cityid",cityId_fromclick);
            startActivity(intent);

            Intent intent2 = new Intent(SearchCity.this,MainActivity.class);
            intent.putExtra("cityid",cityId_fromclick);



            cursor.moveToNext();
        }
        cursor.close();
        cityDatabase.close();
    }
}
