package com.omairtech.simple

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.omairtech.flexitoast.R
import com.omairtech.flexitoast.toast.FlexiToast
import com.omairtech.flexitoast.toast.FlexiToastGravity
import com.omairtech.flexitoast.toast.FlexiToastStyle

class MainActivity : AppCompatActivity() {

    private var flexiToast: FlexiToast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        flexiToast = FlexiToast(this, "Hello World")
    }

    fun topToast(v: View) {
        flexiToast!!.setGravity(FlexiToastGravity.TOP).show()
    }

    fun centerToast(v: View) {
        flexiToast!!.setGravity(FlexiToastGravity.CENTER).show()
    }

    fun bottomToast(v: View) {
        flexiToast!!.setGravity(FlexiToastGravity.BOTTOM).show()
    }

    fun successToast(v: View) {
        flexiToast!!.setStyle(FlexiToastStyle.SUCCESS)
            .setMessage("Success Toast Message with Icon and Background")
            .show()
    }

    fun failureToast(v: View) {
        flexiToast!!.setStyle(FlexiToastStyle.FAILURE)
            .setMessage("Failure Toast Message with Icon and Background")
            .show()
    }

    fun warningToast(v: View) {
        flexiToast!!.setStyle(FlexiToastStyle.WARNING)
            .setMessage("Warning Toast Message with Icon and Background")
            .show()
    }

    fun infoToast(v: View) {
        flexiToast!!.setStyle(FlexiToastStyle.INFO)
            .setMessage("Info Toast Message with Icon and Background")
            .show()
    }

    fun customToast(v: View){
        flexiToast!!.setBackground("#FF018786")
            .setIcon(drawableResId = R.drawable.baseline_add_alert_24)
            .setMessage("Custom Toast Message with Custom Icon and Custom Background")
            .show()
    }

    fun shortLengthToast(view: View) {
        flexiToast!!.setDuration(Toast.LENGTH_SHORT).show()
    }
    fun longLengthToast(view: View) {
        flexiToast!!.setDuration(Toast.LENGTH_LONG).show()
    }

    fun changeFontToast(view: View) {
        flexiToast!!.setTextFont(R.font.cairo_bold).show()
    }

    fun hideIconToast(view: View) {
        flexiToast!!.useIcon(!flexiToast!!.useIcon).show()
    }
}