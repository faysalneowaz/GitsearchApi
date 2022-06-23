package com.example.faysal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.faysal.adapter.GitHubRepoAdapter;
import com.example.faysal.model.GithubRepoModel;
import com.example.faysal.utils.GlobalData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String query_str = "";

    Button btnSearch;
    EditText keyWordInput;
    ProgressDialog pDialog;
    RecyclerView itemRecycleView;

    List<GithubRepoModel> githubRepoModelList = new ArrayList<>();

    GitHubRepoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = findViewById(R.id.btnSearch);
        keyWordInput = findViewById(R.id.keyWardInput);
        itemRecycleView = findViewById(R.id.itemRecycleView);
        itemRecycleView.setHasFixedSize(true);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait..");
        pDialog.setCanceledOnTouchOutside(false);




        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query_str = keyWordInput.getText().toString().trim();
                if(query_str ==""){
                    keyWordInput.setError("Please input query");
                }else{
                    pDialog.show();
                    getAllsortedRepo(query_str,"stars");
                }

            }
        });


    }

    public void getAllsortedRepo(String query, String sortBy) {
        StringRequest stringRequest
                = new StringRequest(
                Request.Method.GET,
                GlobalData.getBase_url() + "?q=" + query + "&sort=" + sortBy,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        query_str ="";
                        githubRepoModelList.clear();


                        try {
                            JSONObject mainResponseObject = new JSONObject(response);
                            JSONArray itemJsonArray = new JSONArray();
                            itemJsonArray = mainResponseObject.getJSONArray("items");

                            JSONObject itemArrayObject = new JSONObject();

                            for (int i = 0; i<itemJsonArray.length();i++){

                                itemArrayObject = itemJsonArray.getJSONObject(i);
                                int starCount =  Integer.parseInt(itemArrayObject.getString("stargazers_count"));
                                int watchCount = Integer.parseInt(itemArrayObject.getString("watchers_count"));
                                int openIssueCount = Integer.parseInt(itemArrayObject.getString("open_issues_count"));
                                String language = itemArrayObject.getString("language");
                                String createdAt = itemArrayObject.getString("created_at");
                                String updatedAt = itemArrayObject.getString("updated_at");
                                String pushedAt = itemArrayObject.getString("pushed_at");

                                githubRepoModelList.add(new GithubRepoModel(watchCount,starCount,language,createdAt,updatedAt,pushedAt,openIssueCount));

                            }

                            pDialog.dismiss();
                            itemRecycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            adapter=new GitHubRepoAdapter(githubRepoModelList,getApplicationContext());
                            itemRecycleView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(MainActivity.this, error.networkResponse.statusCode+" "+error.getMessage(), Toast.LENGTH_SHORT).show();
                        query_str ="";
                        githubRepoModelList.clear();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "token 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");
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
}