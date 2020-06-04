package com.example.currencyconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView cadText;
    TextView chfText;
    TextView usdText;
    TextView jpyText;
    TextView tryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cadText = findViewById(R.id.cadText);
        chfText = findViewById(R.id.chfText);
        usdText = findViewById(R.id.usdText);
        jpyText = findViewById(R.id.jpyText);
        tryText = findViewById(R.id.tryText);
        //github test
    }

    public void getRates(View view){
        DownloadData downloadData = new DownloadData();
        try{
            String url = "http://data.fixer.io/api/latest?access_key=d8e8f4db5f1b00c1ba864d59a0b18790";
            downloadData.execute(url);
        }catch (Exception e){
            //System.out.println(e.toString());
        }
    }

    private class DownloadData extends AsyncTask<String,Void,String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //System.out.println("received data:" + s); //Tüm veriyi alıyor.
            try{
                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base"); //base ile rate gelen jsondaki alanlar
                System.out.println("base :"+base);
                String rates = jsonObject.getString("rates");
                System.out.println("rate :"+ rates);

                JSONObject jsonObject1 = new JSONObject(rates);
                String tl = jsonObject1.getString("TRY");
                System.out.println("TL :"+tl);
                tryText.setText(tl);

            }catch (Exception ex){
                System.out.println(ex.toString());
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();
                while (data > 0) {
                    char character = (char) data;
                    result += character;
                    data = inputStreamReader.read();
                }
                return result;

            }catch (Exception e){
                return null;
            }

        }
    }
}