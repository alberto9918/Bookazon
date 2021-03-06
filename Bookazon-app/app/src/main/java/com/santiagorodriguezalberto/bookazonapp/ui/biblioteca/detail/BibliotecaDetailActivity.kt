package com.santiagorodriguezalberto.bookazonapp.ui.biblioteca.detail

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import com.santiagorodriguezalberto.bookazonapp.R
import com.santiagorodriguezalberto.bookazonapp.common.Constantes
import com.santiagorodriguezalberto.bookazonapp.common.MyApp
import com.santiagorodriguezalberto.bookazonapp.common.Resource
import com.santiagorodriguezalberto.bookazonapp.data.BibliotecaViewModel
import javax.inject.Inject

class BibliotecaDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var bibliotecaViewModel: BibliotecaViewModel
    private lateinit var nombreBiblioteca: String

    @BindView(R.id.tv_dtName)
    lateinit var tv_NombreBiblioteca: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biblioteca_detail)
        (applicationContext as MyApp).getAppCompontent().inject(this)

        ButterKnife.bind(this)

        nombreBiblioteca = intent.getStringExtra(Constantes.INTENT_DETAIL_KEYWORD_LIBRARY_NAME)

        bibliotecaViewModel.getBibliotecaByName(nombreBiblioteca)

        bibliotecaViewModel.biblioteca.observe(this, Observer {response ->
            when(response) {
                is Resource.Success ->  {
                    tv_NombreBiblioteca.text = response.data?.nombre
                }

                is Resource.Loading -> {
                    //CARGANDO
                }

                is Resource.Error -> {
                    Toast.makeText(MyApp.instance,"Error, ${response.message}", Toast.LENGTH_LONG).show()
                }
            }
        })

    }
}
