package com.santiagorodriguezalberto.bookazonapp.ui.copia

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.santiagorodriguezalberto.bookazonapp.R

import com.santiagorodriguezalberto.bookazonapp.ui.copia.dummy.DummyContent
import com.santiagorodriguezalberto.bookazonapp.ui.copia.dummy.DummyContent.DummyItem

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [CopiaFragment.OnListFragmentInteractionListener] interface.
 */
class CopiaFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_copia_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyCopiaRecyclerViewAdapter(DummyContent.ITEMS)
            }
        }
        return view
    }

}
