package com.avalonomnimedia.giphygame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import androidx.navigation.fragment.navArgs
import com.avalonomnimedia.giphygame.service.DictionaryService
import com.avalonomnimedia.giphygame.service.IDictionaryService
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.android.synthetic.main.fragment_results.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
    ): View? = inflater.inflate(R.layout.fragment_results, container, false)

    override fun onStart() {
        super.onStart()

        resultsViewModel.calculateScore(args.searchTerm, args.guess).observe(this) {
            textScore.text = this.requireContext().getString(it.resId, it.score)
        }
    }
}

class ResultsViewModel(private val dictionaryService: IDictionaryService) : ViewModel() {
    fun calculateScore(searchTerm: String, guess: String): LiveData<ResultsData> = liveData {
        val points = if (guess == searchTerm) {
            ResultsData(R.string.results_perfect_score)
        } else {
            val found = dictionaryService.getSynonyms(searchTerm).firstOrNull { it.word == guess }
            if (found != null) {
                ResultsData(R.string.results_some_points, found.score)
            } else {
                ResultsData(R.string.results_no_points)
            }
        }

        emit(points)
    }
}

data class ResultsData(val resId: Int, val score: Int? = null)