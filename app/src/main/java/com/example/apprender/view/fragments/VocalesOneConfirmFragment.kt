package com.example.apprender.view.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.apprender.logica.LeccionStat
import com.example.apprender.R
import com.example.apprender.logica.Session
import com.example.apprender.view.ChapterOneActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.leccion_confirm_dialog.view.*

class VocalesOneConfirmFragment : Fragment() {

    private lateinit var iStopTimer : sendTimeChronometer

    private var estadistica: LeccionStat = LeccionStat()
    private var listaPuntaje: ArrayList<Int> = ArrayList()
    private var listaAciertos: ArrayList<Boolean> = ArrayList()

    private lateinit var txtTiempo: TextView

    val db = FirebaseFirestore.getInstance()
    lateinit var session: Session

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        session = Session(requireContext())

        val view = inflater.inflate(R.layout.fragment_vocales_one_confirm, container, false)

        val txtPuntaje = view.findViewById<TextView>(R.id.txtPuntaje)
        txtTiempo = view.findViewById(R.id.txtTiempo)
        val txtCorrectas = view.findViewById<TextView>(R.id.txtCorrectas)
        val txtIncorrectas = view.findViewById<TextView>(R.id.txtIncorrectas)
        val btnGuardar = view.findViewById<Button>(R.id.btn_guardar)

        listaPuntaje.add(arguments!!.getInt("puntaje_a"))
        listaPuntaje.add(arguments!!.getInt("puntaje_e"))
        listaPuntaje.add(arguments!!.getInt("puntaje_i"))
        listaPuntaje.add(arguments!!.getInt("puntaje_o"))
        listaPuntaje.add(arguments!!.getInt("puntaje_u"))

        listaAciertos.add(arguments!!.getBoolean("acierto_a"))
        listaAciertos.add(arguments!!.getBoolean("acierto_e"))
        listaAciertos.add(arguments!!.getBoolean("acierto_i"))
        listaAciertos.add(arguments!!.getBoolean("acierto_o"))
        listaAciertos.add(arguments!!.getBoolean("acierto_u"))

        val puntaje = estadistica.puntajeTotal(listaPuntaje).toString()
        txtPuntaje.text = puntaje

        val leccionCorrecta = estadistica.cantidadCorrectas(listaAciertos).toString()
        txtCorrectas.text = leccionCorrecta

        val leccionIncorrecta = estadistica.cantidadIncorrectas(listaAciertos).toString()
        txtIncorrectas.text = leccionIncorrecta

        btnGuardar.setOnClickListener {

            val time = arguments!!.getLong("tiempo")

            saveLeccionOne(puntaje.toInt(),time.toInt(),leccionCorrecta.toInt(),leccionIncorrecta.toInt(),true)

        }

        // Inflate the layout for this fragment
        return view
    }

    fun saveLeccionOne(puntaje: Int,tiempo: Int, correctas: Int, incorrectas: Int, estado: Boolean){

        val userData = session.getUserData()
        val rut = userData.get(Session.KEY_RUT)

        val leccion = hashMapOf(
            "puntaje" to puntaje,
            "tiempo" to tiempo,
            "correctas" to correctas,
            "incorrectas" to incorrectas,
            "estado" to estado
        )

        db.collection("usuarios").document(rut!!).collection("capitulo_1")
            .document("leccion_1").set(leccion).addOnCompleteListener {
                if (it.isSuccessful){

                    Log.d("Documento agregado","$leccion")

                    val confirmDialog = LayoutInflater.from(this.activity).inflate(R.layout.leccion_confirm_dialog,null)
                    val builder = AlertDialog.Builder(this.activity).setView(confirmDialog)

                    val alertDialog = builder.show()

                    confirmDialog.btn_continuar.setOnClickListener {
                        alertDialog.dismiss()

                        val intent = Intent(this.activity,ChapterOneActivity::class.java)
                        startActivity(intent)
                    }
                }else{
                    Log.e("Save error", "No se pudo guardar la lección")
                }
            }
    }

    interface sendTimeChronometer{
        fun stopTimer()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        iStopTimer = activity as sendTimeChronometer
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        iStopTimer.stopTimer()
        val leccionTime = arguments!!.getLong("tiempo")
        txtTiempo.text = leccionTime.toString()
    }
}
