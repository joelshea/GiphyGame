package com.avalonomnimedia.giphygame

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?  {
        val view  = inflater.inflate(R.layout.fragment_search, container, false)

        Glide.with(this).load(R.raw.letsplay).into(view.findViewById(R.id.imageLetsPlay))

        return view
    }

    override fun onStart() {
        super.onStart()
        editSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchTerm = v.text.toString()
                if (searchTerm.contains(" ")) {
                    getSpacesAlert(requireContext())?.show()
                    return@setOnEditorActionListener false
                } else {
                    val inputManager: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(v.windowToken, 0)

                    findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToGiphyFragment(searchTerm))
                    return@setOnEditorActionListener true
                }
            }
            return@setOnEditorActionListener false
        }
    }
}