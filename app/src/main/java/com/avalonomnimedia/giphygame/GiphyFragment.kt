package com.avalonomnimedia.giphygame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.giphy.sdk.core.models.enums.MediaType
import com.giphy.sdk.ui.pagination.GPHContent
import kotlinx.android.synthetic.main.fragment_giphy.*

class GiphyFragment : Fragment() {
    val args: GiphyFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_giphy, container, false)

    override fun onStart() {
        super.onStart()
        gifsGridView.content = GPHContent.searchQuery(args.searchTerm)
    }
}