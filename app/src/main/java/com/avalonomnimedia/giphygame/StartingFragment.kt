package com.avalonomnimedia.giphygame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide


class StartingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_starting, container, false);

        Glide.with(this).load(R.raw.welcome).into(view.findViewById(R.id.welcome_gif))

        view.findViewById<Button>(R.id.button_start).setOnClickListener {
            findNavController().navigate(StartingFragmentDirections.actionStartingFragmentToSearchFragment())
        }

        return view
    }
}