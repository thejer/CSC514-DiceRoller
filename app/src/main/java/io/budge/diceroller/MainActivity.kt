package io.budge.diceroller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        oglView.setOnClickListener {
            oglView.spinGuys()
        }
    }

    override fun onPause() {
        super.onPause()
        oglView!!.onPause()
    }

    override fun onResume() {
        super.onResume()
        oglView!!.onResume()
    }

    companion object {
        var isSpinning = false
    }
}
