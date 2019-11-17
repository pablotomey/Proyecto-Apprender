package com.example.apprender.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import com.example.apprender.ILeccionVocalesOne

import com.example.apprender.R
import com.example.apprender.view.Validator

class LeccionSelectEFragment : Fragment() {

    private lateinit var iVocalesOne : ILeccionVocalesOne
    lateinit var btnVerificar : Button
    private lateinit var rgOptions: RadioGroup

    var layout: Int = 0
    private var validator: Validator = Validator()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_leccion_select_e, container, false)
        layout = R.id.leccion_e_layout
        val mLayoutInflater = layoutInflater
        btnVerificar = view.findViewById(R.id.btnVerificar)
        rgOptions = view.findViewById(R.id.radioGroup)

        rgOptions.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.escoba -> {
                    btnVerificar.isEnabled = true
                    btnVerificar.backgroundTintList = ContextCompat.getColorStateList(this@LeccionSelectEFragment.context!!,R.color.btn_green_selector_unpressed)
                }
                R.id.olla -> {
                    btnVerificar.isEnabled = true
                    btnVerificar.backgroundTintList = ContextCompat.getColorStateList(this@LeccionSelectEFragment.context!!,R.color.btn_green_selector_unpressed)
                }
                R.id.uña -> {
                    btnVerificar.isEnabled = true
                    btnVerificar.backgroundTintList = ContextCompat.getColorStateList(this@LeccionSelectEFragment.context!!,R.color.btn_green_selector_unpressed)
                }
            }
        }

        btnVerificar.setOnClickListener {

            when (rgOptions.checkedRadioButtonId) {
                R.id.escoba -> {
                    val puntaje = 5
                    val acierto = true
                    validator.showSnackBar(this.context!!,acierto,mLayoutInflater,view,layout)

                    iVocalesOne.datosLeccionTwo(puntaje,acierto)
                }

                R.id.olla -> {
                    val puntaje = 0
                    val acierto = false
                    validator.showSnackBar(this.context!!,acierto,mLayoutInflater,view,layout)

                    iVocalesOne.datosLeccionTwo(puntaje,acierto)
                }

                R.id.uña -> {
                    val puntaje = 0
                    val acierto = false
                    validator.showSnackBar(this.context!!,acierto,mLayoutInflater,view,layout)
                    iVocalesOne.datosLeccionTwo(puntaje,acierto)
                }
            }
        }

        // Inflate the layout for this fragment
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        iVocalesOne = activity as ILeccionVocalesOne
    }
}
