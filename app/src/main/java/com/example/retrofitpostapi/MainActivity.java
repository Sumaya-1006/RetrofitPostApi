package com.example.retrofitpostapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText nameEditText,jobEditText;
    TextView responseText;
    ProgressBar progressBar;
    Button sendBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        jobEditText = findViewById(R.id.jobEditText);
        responseText = findViewById(R.id.responseId);
        progressBar = findViewById(R.id.progressBarId);
        sendBtn = findViewById(R.id.sendBtnId);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameEditText.getText().toString().isEmpty() && jobEditText.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter your values", Toast.LENGTH_SHORT).show();
                    return;
                }
                postData(nameEditText.getText().toString(), jobEditText.getText().toString());

            }
        });


    }

    private void postData(String name, String job) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        DataModel dataModel = new DataModel(name, job);

        Call<DataModel> call = retrofitApi.createPost(dataModel);
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                Toast.makeText(MainActivity.this, "Data added to Api", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                nameEditText.setText("");
                jobEditText.setText("");

                DataModel responseFromAPI = response.body();
                String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getName() + "\n" + "Job: " + responseFromAPI.getJob();
                responseText.setText(responseString);

            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                responseText.setText("Error found is : " +t.getMessage());

            }
        });


    }

}