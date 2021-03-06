package com.santiagorodriguezalberto.bookazonapp.ui.biblioteca.list

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.santiagorodriguezalberto.bookazonapp.R
import com.santiagorodriguezalberto.bookazonapp.common.MyApp
import com.santiagorodriguezalberto.bookazonapp.common.Resource
import com.santiagorodriguezalberto.bookazonapp.data.BibliotecaViewModel
import com.santiagorodriguezalberto.bookazonapp.model.Biblioteca
import com.santiagorodriguezalberto.bookazonapp.ui.DashboardActivity
import javax.inject.Inject


class BibliotecaFragment : Fragment() {
    @Inject
    lateinit var bibliotecaViewModel: BibliotecaViewModel
    private lateinit var bibliotecaAdapter: MyBibliotecaRecyclerViewAdapter
    private var bibliotecas: List<Biblioteca> = ArrayList()
    private var columnCount = 1
    private lateinit var nombreBiblioteca: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).getAppCompontent().inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_biblioteca_list, container, false)

        bibliotecaAdapter = MyBibliotecaRecyclerViewAdapter()

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = bibliotecaAdapter
            }
        }

        bibliotecaViewModel.getAll()
        bibliotecaViewModel.bibliotecas.observe(viewLifecycleOwner, Observer {response ->
            when(response) {
                is Resource.Success ->  {
                    bibliotecas = response.data!!
                    bibliotecaAdapter.setData(bibliotecas)
                }

                is Resource.Loading -> {
                    //CARGANDO
                }

                is Resource.Error -> {
                    Toast.makeText(MyApp.instance,"Error, ${response.message}", Toast.LENGTH_LONG).show()
                }
            }
        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_biblioteca_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_show_map -> {

                var i = Intent(MyApp.instance, DashboardActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                MyApp.instance.startActivity(i)

            }
        }
        return super.onOptionsItemSelected(item)
    }

}
