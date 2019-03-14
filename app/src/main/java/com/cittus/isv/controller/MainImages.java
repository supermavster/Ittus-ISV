package com.cittus.isv.controller;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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
import com.cittus.isv.model.CittusImage;
import com.cittus.isv.model.SliderUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// TODO: Cambiar a Fragment
public class MainImages {

    // Variables Globals
    Spinner spinner;
    Boolean sliderOn = false;

    // Variables locals - IMG
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private String imgURL;
    RequestQueue rq;
    List<SliderUtils> sliderImg;
    ViewPagerAdapter viewPagerAdapter;

    Context context;


    private void intiProcess() {
        // Make Slider
        getRequest();

        // Set Process to Click
        processClick();
    }

    int actionImages = 5;
    String titleMain = "";
    String request_url;

    public void getValuesMain(CittusImage imageSelect, Context context, ViewPager viewPagerTemp, LinearLayout SliderDotsTemp, Spinner spinCode) {

        // Select Values
        String titleMain = imageSelect.getTitle();
        String urlImg = imageSelect.getUrl_img();
        Integer action = imageSelect.getAction();

        // Set Variables
        this.titleMain = titleMain;//;intent.getStringExtra("title");
        request_url = urlImg;//intent.getStringExtra("url_img");
        actionImages = action;//intent.getIntExtra("Action",5);

        // Init Varaibles Main

        // Init Elements Base (Consult and Set)
        sliderImg = new ArrayList<>();
        this.context = context;

        rq = CustomVolleyRequest.getInstance(this.context).getRequestQueue();

        // Init Elements - View
        viewPager = viewPagerTemp;//(ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = SliderDotsTemp;//(LinearLayout) findViewById(R.id.SliderDots);

        // Selection of the spinner
        spinner = spinCode;//(Spinner) findViewById(R.id.spin_code_images);

        // Init Process
        intiProcess();
    }

    private void processClick() {
        // Call the window of Main Image
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // Set selected Action
            @Override
            public void onPageSelected(int position) {
                sliderOn = true;
                setElementByPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // Spinner Actions

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub

                String ss = spinner.getSelectedItem().toString();
                for (int i = 0; i < arrayCodes.length; i++) {
                    if (arrayCodes[i] == ss) {
                            /*
                            int positionTemp = (i);
                            String imageUrl = arrayImages[positionTemp];
                            sliderImg = new ArrayList<>();
                            SliderUtils sliderUtils = new SliderUtils();
                            sliderUtils.setSliderImageUrl(imageUrl);
                            sliderImg.add(sliderUtils);
                            setSlider();*/
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    String arrayCodes[];
    String arrayImages[];

    public void getRequest() {
        // Init Request JSOn
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Init Variables Base
                arrayCodes = new String[response.length()];
                arrayImages = new String[response.length()];
                for (int i = 0; i < response.length(); i++) {
                    // Make the Object Base
                    SliderUtils sliderUtils = new SliderUtils();
                    try {
                        // Get the object JSPM
                        JSONObject jsonObject = response.getJSONObject(i);
                        // Get And Set Data from te JSON
                        arrayCodes[i] = jsonObject.getString("code");
                        arrayImages[i] = jsonObject.getString("image_url");
                        imgURL = jsonObject.getString("image_url");
                        // Set Imagen parameter
                        sliderUtils.setSliderImageUrl(imgURL);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Add Object to the Array
                    sliderImg.add(sliderUtils);
                }

                //setSpinner(arrayCodes, arrayImages);
                // Set Elements Get To the Slider
                setSlider();
                // Set Elements Get to the Spinner
                setSpinner();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Make Request
        CustomVolleyRequest.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    private void setSlider() {
        // Init Elements Base - Adapters
        viewPagerAdapter = new ViewPagerAdapter(sliderImg, context, spinner);
        viewPager.setAdapter(viewPagerAdapter);
        // Get Count
        dotscount = viewPagerAdapter.getCount();
        // Check Elements
        if (dotscount > 0) {
            // Make Dots by Count
            dots = new ImageView[dotscount];
            for (int i = 0; i < dotscount; i++) {
                // Set location and move to do it
                dots[i] = new ImageView(context);
                dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.non_active_dot));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(8, 0, 8, 0);
                sliderDotspanel.addView(dots[i], params);
            }
            // Set image at select the dot
            dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));
        }
    }

    private void setSpinner() {
        // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayCodes);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
    }

    private void setElementByPosition(int position) {
        // Move another Elements (DOTS)
        for (int i = 0; i < dotscount; i++) {
            dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.non_active_dot));
        }
        // Set Image
        dots[position].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));
        // Set element in Spinner
        if (position >= 0) {
            spinner.setSelection(position);
        }
    }


}