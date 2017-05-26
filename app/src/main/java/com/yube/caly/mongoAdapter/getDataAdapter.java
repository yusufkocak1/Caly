package com.yube.caly.mongoAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yusuf on 23.05.2017.
 */

public class getDataAdapter extends AsyncTask<String, Void, String> {

    public getDataAdapter(Activity activity) {
        this.activity = activity;
    }

    Activity activity;
    ProgressDialog progressDialog;
    String mResult="unsuccess";

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            return getData(params[0]);
        } catch (IOException ex) {
            return "Network error !";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        //set data response to textView

    }

    private String getData(String urlPath) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader =null;

        try {
            //Initialize and config request, then connect to server
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(10000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");// set header
            urlConnection.connect();

            //Read data response from server
            InputStream inputStream = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line).append("\n");
            }

        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        return result.toString();
    }
}
