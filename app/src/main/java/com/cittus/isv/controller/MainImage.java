package com.cittus.isv.controller;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.cittus.isv.R;
import com.cittus.isv.complements.slider.CustomVolleyRequest;
import com.cittus.isv.complements.slider.ViewPagerAdapter;
import com.cittus.isv.model.SliderUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainImage extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    RequestQueue rq;
    List<SliderUtils> sliderImg;
    ViewPagerAdapter viewPagerAdapter;

    String request_url = "http://172.20.1.13/ittus-senalesviales/queries/sliderjsonoutput.php?signal=turis";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_image);
        intiProcess();
    }

    private void intiProcess(){
        // Intent Variable
        Intent intent = getIntent();

        // Get Elements Base (Consult and Set)
        rq = CustomVolleyRequest.getInstance(this).getRequestQueue();
        sliderImg = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        // Set Template New Activity
        TextView title = (TextView) findViewById(R.id.lbl_title_images);
        title.setText(intent.getStringExtra("title"));

        // Url Main Base - Imgs
        //request_url = intent.getStringExtra("url_img");

        // Active Select
        findViewById(R.id.codeMain).setVisibility(intent.getBooleanExtra("code",true)? View.VISIBLE:View.GONE);
        findViewById(R.id.descriptionMain).setVisibility(intent.getBooleanExtra("description",true)? View.VISIBLE:View.GONE);
        // Make Spinners
        //getRequest("code");

        // Make Slider
        getRequest("url_img");
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
    private void setSpinner(String[] arrayItems){
        // Selection of the spinner
        final Spinner spinner = (Spinner) findViewById(R.id.spin_code_images);
        // Array of choices
        Log.i("INFO",arrayItems.toString());
        // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, arrayItems);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);

        // Set Acctions Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                String ss = spinner.getSelectedItem().toString();

                String imageUrl = "http://172.20.1.13/Ittus-SenalesViales/imagenes/senales-informativas-turisticas-fotos/"+ss+".png";

                sliderImg = new ArrayList<>();

                SliderUtils sliderUtils = new SliderUtils();
                sliderUtils.setSliderImageUrl(imageUrl);
                sliderImg.add(sliderUtils);
                setSlider();
                 Toast.makeText(getBaseContext(), ss, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

    }

    private void setSlider(){
        viewPagerAdapter = new ViewPagerAdapter(sliderImg, MainImage.this);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(MainImage.this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
    }

    public void getRequest(final String sql){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String arrayCodes[] = new String[response.length()];
                for (int i = 0; i < response.length(); i++) {

                    SliderUtils sliderUtils = new SliderUtils();

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        arrayCodes[i] = jsonObject.getString("code");
                        sliderUtils.setSliderImageUrl(jsonObject.getString("image_url"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    sliderImg.add(sliderUtils);

                }

                setSpinner(arrayCodes);
                setSlider();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        CustomVolleyRequest.getInstance(this).addToRequestQueue(jsonArrayRequest);

    }

}
