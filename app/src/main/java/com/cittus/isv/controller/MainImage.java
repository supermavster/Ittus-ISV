package com.cittus.isv.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
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
import com.cittus.isv.view.MainActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainImage extends AppCompatActivity {

    // Variables Globals
    String request_url;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_image);
        // Init Process
        intiProcess();
    }

    private void intiProcess() {
        // Init Variables
        initVariables();

        // Get Elements Base (Consult and Set)
        getValuesMain();

        // Make Slider
        getRequest();

        // Set Process to Click
        processClick();

        // BTN Save
        saveElements();
    }

    private void initVariables() {

        // Init Elements Base (Consult and Set)
        sliderImg = new ArrayList<>();
        rq = CustomVolleyRequest.getInstance(this).getRequestQueue();

        // Init Elements - View
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        // Selection of the spinner
        spinner = (Spinner) findViewById(R.id.spin_code_images);
    }

    private void getValuesMain() {
        // Intent Variable
        Intent intent = getIntent();

        // Set Template New Activity
        TextView title = findViewById(R.id.lbl_title_images);
        title.setText(intent.getStringExtra("title"));

        // Url Main Base - Imgs
        request_url = intent.getStringExtra("url_img");

        // Active Select
        findViewById(R.id.codeMain).setVisibility(intent.getBooleanExtra("code", true) ? View.VISIBLE : View.GONE);
        //findViewById(R.id.descriptionMain).setVisibility(intent.getBooleanExtra("description", true) ? View.VISIBLE : View.GONE);

        // Url Main Base - Imgs
        request_url = intent.getStringExtra("url_img");
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
        CustomVolleyRequest.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    private void setSlider() {
        // Init Elements Base - Adapters
        viewPagerAdapter = new ViewPagerAdapter(sliderImg, MainImage.this, spinner);
        viewPager.setAdapter(viewPagerAdapter);
        // Get Count
        dotscount = viewPagerAdapter.getCount();
        // Check Elements
        if (dotscount > 0) {
            // Make Dots by Count
            dots = new ImageView[dotscount];
            for (int i = 0; i < dotscount; i++) {
                // Set location and move to do it
                dots[i] = new ImageView(MainImage.this);
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(8, 0, 8, 0);
                sliderDotspanel.addView(dots[i], params);
            }
            // Set image at select the dot
            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        }
    }

    private void setSpinner() {
        // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayCodes);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
    }

    private void setElementByPosition(int position) {
        // Move another Elements (DOTS)
        for (int i = 0; i < dotscount; i++) {
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
        }
        // Set Image
        dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        // Set element in Spinner
        if (position >= 0) {
            spinner.setSelection(position);
        }
    }

    private void saveElements() {
        findViewById(R.id.btn_save_image).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ArrayList elementsBase = new ArrayList<String>();
                if (spinner != null) {
                    String ss = spinner.getSelectedItem().toString();

                    for (int i = 0; i < arrayCodes.length; i++) {
                        if (arrayCodes[i] == ss) {
                            elementsBase.add("'Number':'" + i + "'");
                            elementsBase.add("'Code':'" + arrayCodes[i] + "'");
                            elementsBase.add("'ImgSelected':'" + arrayImages[i] + "'");
                        }
                    }


                    // Array
                    Intent intent = getIntent();
                    intent.putExtra("getData", elementsBase);
                    setResult(5, intent);
                    finish();
                }
                return true;
            }
        });


    }
}