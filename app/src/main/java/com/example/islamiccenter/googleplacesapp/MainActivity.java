package com.example.islamiccenter.googleplacesapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.islamiccenter.googleplacesapp.Adapter.PlacesAdapter;
import com.example.islamiccenter.googleplacesapp.PlacesModel.PlacesModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.islamiccenter.googleplacesapp.R.id.imageView;

public class MainActivity extends AppCompatActivity {
    EditText name ;
    Button search ;
    ListView listView;
    PlacesModel [] placemodels;
    Gson gson = new Gson();
    PlacesAdapter placesadapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText)findViewById(R.id.editText_name);
        search= (Button)findViewById(R.id.button_search);
        listView=(ListView)findViewById(R.id.ListView_places);
        progressDialog=new ProgressDialog(MainActivity.this);

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        String url ="http://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670,151.1957&radius=500&types=food&name=cruise&key=AIzaSyBp4c8yTUU9vhYmU73ZMHBOWHIinW5q0b8";

        myAsyncTask.execute(url);
    }



    private class MyAsyncTask extends AsyncTask<String , Void ,PlacesModel[] > {

        protected void onPreExecute() {

        }
        OkHttpClient client = new OkHttpClient();

        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        protected PlacesModel[] doInBackground(String... url) {
            try {
                String s = run(url[0]);
                Log.d("yarb tfla7", s);
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                placemodels = gson.fromJson(jsonArray.toString(), PlacesModel[].class);
                return placemodels;

            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

//        protected void onProgressUpdate(Progress... values) {
//            // Executes whenever publishProgress is called from doInBackground
//            // Used to update the progress indicator
//            progressBar.setProgress(values[0]);
//        }

        protected void onPostExecute(PlacesModel[]placesModels) {
            progressDialog.dismiss();
            if(placesModels!=null) {
                placesadapter = new PlacesAdapter(MainActivity.this, placemodels);
                listView.setAdapter(placesadapter);

            }
        }
    }
}
