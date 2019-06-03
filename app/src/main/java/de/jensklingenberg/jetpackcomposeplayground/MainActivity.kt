package de.jensklingenberg.jetpackcomposeplayground

import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.setContent
import androidx.ui.painting.Image

lateinit var getPreferences: (Int) -> SharedPreferences
lateinit var Image: (Int) -> Image



class MainActivity : AppCompatActivity() {

    init {
        getPreferences = this::getPreferences
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        Image = {
            Image(
                BitmapFactory.decodeResource(applicationContext.resources, it)
            )
        }

        setContent {
            MyComposeApp()
        }


    }

    override fun onBackPressed() {
    }


}

