package com.avalonomnimedia.giphygame

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.avalonomnimedia.giphygame.service.DictionaryResponse
import com.avalonomnimedia.giphygame.service.DictionaryService
import com.giphy.sdk.core.models.enums.MediaType
import com.giphy.sdk.ui.pagination.GPHContent
import kotlinx.android.synthetic.main.fragment_giphy.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GiphyFragment : Fragment() {
    private val args: GiphyFragmentArgs by navArgs()
    private var list: List<DictionaryResponse>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_giphy, container, false)

    override fun onStart() {
        super.onStart()
        gifsGridView.content = GPHContent.searchQuery(args.searchTerm)

        editAnswer.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val guess = v.text.toString()
                if (guess.contains(" ")) {
                    AlertDialog.Builder(context)
                        .setTitle("Uh oh")
                        .setMessage("On word searches only, please.") // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.ok, null) // A null listener allows the button to dismiss the dialog and take no further action.
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                } else {
                    findNavController().navigate(GiphyFragmentDirections.actionGiphyFragmentToResultsFragment(args.searchTerm, guess))
                    val inputManager: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(v.windowToken, 0)
                    return@setOnEditorActionListener true
                }
            }
            return@setOnEditorActionListener false
        }
    }
}