package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var inOutTv : TextView?=null
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inOutTv =findViewById(R.id.inOutTv)
    }

    fun onDigit(view: View) {
        if (view is Button) {
            if (inOutTv?.text == "0") {
                inOutTv?.text = ""
            }
            inOutTv?.append(view.text)
            lastNumeric=true
        }
    }
    fun onClear(view:View){
        if (view is Button) {
                inOutTv?.text = ""
        }
    }

    fun onDecimalPoint(view:View){
        if(lastNumeric && !lastDot){
            inOutTv?.append(".")
            lastNumeric= false
            lastDot= true
        }
    }
    fun onOperator(view:View){
        inOutTv?.text?.let {

            if(lastNumeric && !isOperator(it.toString())){
                inOutTv?.append((view as Button).text)
                lastNumeric= false
                lastDot=false
            }

        }


    }

    fun onEqual(view:View){
        if(lastNumeric){
            var tvValue = inOutTv?.text.toString()
            var prefix =""
            try {

                if(tvValue.startsWith("-")){
                    prefix="-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")

                    var one = splitValue[0]
                    var second = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    inOutTv?.text = removeZeroAfterDot((one.toDouble() - second.toDouble()).toString())
                } else if(tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var second = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    inOutTv?.text = removeZeroAfterDot((one.toDouble() + second.toDouble()).toString())
                } else if(tvValue.contains("x")) {
                    val splitValue = tvValue.split("x")

                    var one = splitValue[0]
                    var second = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    inOutTv?.text = removeZeroAfterDot((one.toDouble() * second.toDouble()).toString())
                } else if(tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    var second = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    inOutTv?.text = removeZeroAfterDot((one.toDouble() / second.toDouble()).toString())
                }
            }catch(e:Exception){
//                Log.d("Error: ", "${e}")
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result:String):String{
        return if (result.endsWith(".0")) {
            result.dropLast(2)
        } else {
            result
        }

    }

    private fun isOperator(value:String):Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/")
                    || value.contains("+")
                    || value.contains("*")
                    || value.contains("-")
        }
    }
}