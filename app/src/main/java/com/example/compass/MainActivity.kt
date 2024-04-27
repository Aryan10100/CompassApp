package com.example.compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.Image
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() ,SensorEventListener {
    var sensor :Sensor?=null
    var sensorManager :SensorManager?=null
    lateinit var compassImage: ImageView
    lateinit var rotationText: TextView
   ///to get the degree
    var currentdegree=0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
         sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        setContentView(R.layout.activity_main)
       compassImage= findViewById<ImageView>(R.id.CompassImage)
        rotationText=findViewById<TextView>(R.id.RotationText)
    }

    override fun onSensorChanged(event : SensorEvent?) {
       //to get the value of degree
        var degree = Math.round(event!!.values[0])
        rotationText.text=degree.toString() + "degrees"
        ///Animation
        var rotationAnimation=RotateAnimation(currentdegree,(-degree).toFloat(),
        Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f)
        rotationAnimation.duration=210
        rotationAnimation.fillAfter=true

        compassImage.startAnimation(rotationAnimation)
        currentdegree=(-degree).toFloat()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
 // register a listener
    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, sensor,SensorManager.SENSOR_DELAY_NORMAL)
    }
//unregister a listener
    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }
}