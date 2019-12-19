package com.away0x.anim.vector

import android.annotation.TargetApi
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.away0x.anim.R

class VectorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vector)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun anim(view: View) {
        val img = view as ImageView
        val drawable = img.drawable as AnimatedVectorDrawable?

        drawable?.start()
    }

}
