package com.example.whatstheweather;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

//https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22
public class MainActivity extends AppCompatActivity {

    public void check(View view)
    {
        TextView tv = (TextView)findViewById(R.id.resTextView);
        EditText name = (EditText)findViewById(R.id.cityEditText);
        String city = name.getText().toString();
        String ans ="";
        tv.setText("");
        DownloadData dd = new DownloadData();



        try {

            String encodeName = URLEncoder.encode(city,"UTF-8");
            String samp = dd.execute("https://openweathermap.org/data/2.5/weather?q="+city+"&appid=b6907d289e10d714a6e88b30761fae22").get();
            JSONObject myJson = new JSONObject(samp);
            String weatherInfo = myJson.getString("weather");
            JSONArray arr = new JSONArray(weatherInfo);


            for(int i =0;i<arr.length();i++)
            {
                JSONObject myPart = arr.getJSONObject(i);
                ans+=myPart.getString("main");
                ans+="::";
                ans+=myPart.getString("description");
                ans+="\n";

            }









        }
        catch (Exception e)
        {

        }

   tv.setText(ans);

        InputMethodManager mgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(name.getWindowToken(),0);

    }


              public class DownloadData extends AsyncTask<String,Void,String>
                  {


                      @Override
                      protected String doInBackground(String... urls) {

                          String ans ="";
                          try {


                              URL url = new URL(urls[0]);
                              HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();

                              InputStream in = myConnection.getInputStream();
                              InputStreamReader reader = new InputStreamReader(in);

                              int x = reader.read();

                              while (x != -1) {
                                  ans += ((char) x + "");

                                  x = reader.read();


                              }


                          }
                          catch (Exception e)
                          {

                          }



                             return ans;
                      }


                      @Override
                      protected void onPostExecute(String s) {
                          super.onPostExecute(s);





                      }
                  }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
