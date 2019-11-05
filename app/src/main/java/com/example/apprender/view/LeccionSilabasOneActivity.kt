package com.example.apprender.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.apprender.R
import com.example.apprender.view.fragments.SepSilabasOneFragment

class LeccionSilabasOneActivity : AppCompatActivity() {

    var manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leccion_silabas_one)

        createFragment()
    }

    private fun createFragment(){
        val transaction = manager.beginTransaction()
        val fragment = SepSilabasOneFragment()
        transaction.replace(R.id.leccion_slbs_one_container,fragment)
        transaction.commit()
    }
}