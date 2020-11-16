package com.avalonomnimedia.giphygame

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onStart() {
        super.onStart()
        editSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchTerm = v.text.toString()
                if (searchTerm.contains(" ")) {
                    AlertDialog.Builder(context)
                        .setTitle("Uh oh")
                        .setMessage("On word searches only, please.") // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.ok, null) // A null listener allows the button to dismiss the dialog and take no further action.
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                } else {
                    findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToGiphyFragment(searchTerm))
                    val inputManager: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(v.windowToken, 0)
                    return@setOnEditorActionListener true
                }
            }
            return@setOnEditorActionListener false
        }
    }
}