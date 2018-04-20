package com.example.peterperez.colorpickerintent

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import kotlinx.android.synthetic.main.color_picker.*
import java.io.Serializable
data class ColorCustom(var redval: Int, var grnval: Int, var blueval: Int):Serializable

class ColorPicker : AppCompatActivity() {
    //data class ColorCustom(var redval: Int, var grnval: Int, var blueval: Int): Serializable
    var newColor :ColorCustom= ColorCustom(0,0,0)

    val ACTION_COLOR="com.example.peterperez.ACTION_COLOR"
    lateinit var rslider: SeekBar
    lateinit var gslider: SeekBar
    lateinit var bslider: SeekBar
    lateinit var value : TextView
    lateinit var surface : SurfaceView
    lateinit var rval : EditText
    lateinit var bval : EditText
    lateinit var gval : EditText
    var REQUEST_ID:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.color_picker)

        rslider = r_slider
        gslider = g_slider
        value = textv_result
        bslider = b_slider
        surface = surface_view
        rval = redval
        bval = blueval
        gval = greenval
        rslider.max=255
        gslider.max=255
        bslider.max=255
        var savebutton : Button =savebutton


       handleIntent()



        fun updateSurface():Unit {
            var rslide = rslider.getProgress()
            var gslide= gslider.getProgress()
            var bslide= bslider.getProgress()

            var status =0xff000000 + rslide*0x10000 + gslide*0x100 + bslide
            value.text= Integer.toHexString(status.toInt())
            surface.setBackgroundColor(status.toInt())

            reset_button.setOnClickListener{
                surface.setBackgroundColor(0)
                rslider.setProgress(0)
                gslider.setProgress(0)
                bslider.setProgress(0)
            }
        }

        rslider.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                rval.setText(rslider.getProgress().toString())
                updateSurface()

            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        gslider.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                gval.setText(gslider.getProgress().toString())
                updateSurface()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        bslider.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                bval.setText(bslider.getProgress().toString())
                updateSurface()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        rval.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                rval.setSelection(rval.getText().length)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                rval.setSelection(rval.getText().length)
                try{rslider.setProgress(Integer.parseInt(rval.getText().toString()))
                    updateSurface()}
                catch(ex : Exception){
                    value.text="invalid value"
                    rslider.setProgress(0)
                }
            }
        })

        bval.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                bval.setSelection(bval.getText().length)
                try{bslider.setProgress(Integer.parseInt(bval.getText().toString()))
                    updateSurface()}
                catch(ex : Exception){
                    value.text="invalid value"
                    bslider.setProgress(0)
                }
            }
        })

        gval.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                gslider.setProgress(Integer.parseInt(gval.getText().toString()))
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                gval.setSelection(gval.getText().length)
                try{gslider.setProgress(Integer.parseInt(gval.getText().toString()))
                    updateSurface()}
                catch(ex : Exception){
                    value.text="invalid value"
                    gslider.setProgress(0)
                }
            }
        })
    }

    private fun handleIntent() {

        val info = intent.extras
        if (info != null) {
            if (info.containsKey("Color a")) {
                savebutton.visibility = View.VISIBLE
                savebutton.setOnClickListener {
                    sendA()
                }
            } else if (info.containsKey("Color b")) {
                savebutton.visibility = View.VISIBLE
                savebutton.setOnClickListener {
                    sendB()
                }
            }
        }

    }
    fun sendA(){
        var r=rslider.progress
        var g=gslider.progress
        var b=bslider.progress
        var colorToSend: String = ("$r $g $b")

        val returnIntent: Intent = Intent()
        returnIntent.putExtra("Return_Color",colorToSend)
        intent.putExtra("Color A",colorToSend)
        setResult(RESULT_OK, intent)
        super.finish()
    }

    fun sendB(){
        var r=rslider.progress
        var g=gslider.progress
        var b=bslider.progress
        var colorToSend: String = ("$r $g $b")

        val returnIntent: Intent = Intent()
        returnIntent.putExtra("Return_Color",colorToSend)
        intent.putExtra("Color B",colorToSend)
        setResult(RESULT_OK, intent)
        super.finish()
    }

}
