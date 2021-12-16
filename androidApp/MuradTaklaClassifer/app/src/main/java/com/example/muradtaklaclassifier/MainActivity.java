package com.example.muradtaklaclassifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button mCheckButton;
    EditText mInputEditTextView;
    TextView mResultTextView;
    final String url ="https://muradtaklaclassifier.herokuapp.com/predict?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResultTextView=findViewById(R.id.result);
        mInputEditTextView=findViewById(R.id.inputext);
        mCheckButton=findViewById(R.id.checkBtn);

        mCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=mInputEditTextView.getText().toString();
                getPredcition(text);
                hideKeyboardFrom(getApplicationContext(),mInputEditTextView);

            }
        });

    }

    private void getPredcition(String text){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url_query=url+text;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            int prediction=Integer.parseInt(jsonObject.getString("prediction"));
                            float mtProb=Float.parseFloat(jsonObject.getString("murad_takla_prob"));
                            float nmtProb=Float.parseFloat(jsonObject.getString("non_murad_takla_prob"));
                            if(prediction==1){
                                String formatedString=getString(R.string.murad_takla_string, mtProb*100);
                                mResultTextView.setText(formatedString);
                                mResultTextView.setTextColor(getColor(R.color.red));
                            }else{
                                mResultTextView.setText(getString(R.string.non_murad_takla));
                                mResultTextView.setTextColor(getColor(R.color.green));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}