package com.avalonomnimedia.giphygame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.avalonomnimedia.giphygame.service.DictionaryService
import com.avalonomnimedia.giphygame.service.IDictionaryService
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.android.synthetic.main.fragment_results.*


class ResultsFragment : Fragment() {
    private val args: ResultsFragmentArgs by navArgs()
    private val resultsViewModel by lazy {
        // Normally I would set this up with a more dedicated and smarter
        // dependency injection library, but for now this will allow use to
        // separate concerns enough for testing.
        ResultsViewModel(DictionaryService(HttpClient() {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_results, container, false)

        Glide.with(this).load(R.raw.wait).transition(withCrossFade()).into(view.findViewById(R.id.imageResults))

        view.findViewById<Button>(R.id.button_play_again).setOnClickListener {
            findNavController().navigate(ResultsFragmentDirections.actionResultsFragmentToSearchFragment())
        }
        return view
    }

    override fun onStart() {
        super.onStart()

        resultsViewModel.calculateScore(args.searchTerm, args.guess).observe(this) {
            textScore.text = this.requireContext().getString(it.textId, it.score)
            Glide.with(this).load(it.gifId).transition(withCrossFade()).into(imageResults)
        }
    }
}

class ResultsViewModel(private val dictionaryService: IDictionaryService) : ViewModel() {
    fun calculateScore(searchTerm: String, guess: String): LiveData<ResultsData> = liveData {
        val points = if (guess == searchTerm) {
            ResultsData(R.string.results_perfect_score, gifId = R.raw.win)
        } else {
            val found = dictionaryService.getSynonyms(searchTerm).firstOrNull { it.word == guess }
            if (found != null) {
                ResultsData(R.string.results_some_points, found.score, R.raw.soso)
            } else {
                ResultsData(R.string.results_no_points, gifId = R.raw.bad)
            }
        }

        emit(points)
    }
}

data class ResultsData(val textId: Int, val score: Int? = null, val gifId: Int)