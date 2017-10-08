package com.example.islamiccenter.googleplacesapp;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.islamiccenter.googleplacesapp.Adapter.PlacesAdapter;
import com.example.islamiccenter.googleplacesapp.PlacesModel.PlacesModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText)findViewById(R.id.editText_name);
        search= (Button)findViewById(R.id.button_search);
        listView=(ListView)findViewById(R.id.ListView_places);
    }
    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private class MyAsyncTask extends AsyncTask<String , Void , Bitmap> {
        protected void onPreExecute(String response) {

            JsonObject jsonObject=new JsonObject(response);
            JsonArray jsonArray=jsonObject.getAsJsonArray("results");
            placemodels = gson.fromJson(jsonArray.toString(), PlacesModel[].class);

            // Runs on the UI thread before doInBackground
            // Good for toggling visibility of a progress indicator
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        protected Bitmap doInBackground(String... strings) {
            placesadapter=new PlacesAdapter(MainActivity.this ,placemodels);
            listView.setAdapter(placesadapter);
            // Some long-running task like downloading an image.
            Bitmap = downloadImageFromUrl(strings[0]);
            return someBitmap;
        }

//        protected void onProgressUpdate(Progress... values) {
//            // Executes whenever publishProgress is called from doInBackground
//            // Used to update the progress indicator
//            progressBar.setProgress(values[0]);
//        }

        protected void onPostExecute(Bitmap result) {
            // This method is executed in the UIThread
            // with access to the result of the long running task
            imageView.setImageBitmap(result);
            // Hide the progress bar
            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }
    }
}
