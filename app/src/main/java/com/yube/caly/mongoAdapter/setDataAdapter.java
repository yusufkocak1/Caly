package com.yube.caly.mongoAdapter;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yusuf on 23.05.2017.
 */

public class setDataAdapter extends AsyncTask<String, Void, String> {
    String mresult = "unsucces",
            data = "boş kayıt";

    public String setdata(String data) {
        this.data = data;

        return mresult;
    }

    ProgressDialog progressDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

       /* progressDialog = new ProgressDialog(setDataAdapter.this);
        progressDialog.setMessage("Inserting data...");
        progressDialog.show();*/
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            return postData(params[0], params[1], params[2], params[3]);
        } catch (IOException ex) {
            return "Network error !";
        } catch (JSONException ex) {
            return "Data Invalid !";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        mresult = result;

        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private String postData(String urlPath, String data, String date, String username) throws IOException, JSONException {

        StringBuilder result = new StringBuilder();
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;

        try {
            //Create data to send to server
            JSONObject dataToSend = new JSONObject();
            dataToSend.put("daily", data);
            dataToSend.put("date", date);
            dataToSend.put("username", username);


            //Initialize and config request, then connect to server.
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(10000 /* milliseconds */);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);  //enable output (body data)
            urlConnection.setRequestProperty("Content-Type", "application/json");// set header
            urlConnection.connect();

            //Write data into server
            OutputStream outputStream = urlConnection.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(dataToSend.toString());
            bufferedWriter.flush();

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
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }

        return result.toString();
    }
}
