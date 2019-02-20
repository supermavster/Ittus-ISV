package com.cittus.isv.view.horizontal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cittus.isv.R
import kotlinx.android.synthetic.main.activity_horizontal_main.*

class ActivityHorizontalMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horizontal_main)

        btn_save_ths.setOnClickListener(View.OnClickListener {
            finish()
        })

    }
}
