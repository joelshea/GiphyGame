package com.avalonomnimedia.giphygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.giphy.sdk.ui.Giphy

const val GIPHY_API_KEY = "HZa6Kc2AJWWn43bw8igOBD1KGRgmgLqZ"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Giphy.configure(this, GIPHY_API_KEY)
    }
}