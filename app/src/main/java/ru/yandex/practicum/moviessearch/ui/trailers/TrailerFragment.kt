package ru.yandex.practicum.moviessearch.ui.trailers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.yandex.practicum.moviessearch.databinding.FragmentTrailersBinding
import ru.yandex.practicum.moviessearch.presentation.trailers.TrailerState
import ru.yandex.practicum.moviessearch.presentation.trailers.TrailersViewModel

class TrailerFragment: Fragment() {

    companion object {

        private const val ARGS_MOVIE_ID = "movie_id"

        fun createArgs(movieId: String): Bundle =
            bundleOf(ARGS_MOVIE_ID to movieId)
    }

    private var movieId: String? = null
    private val viewModel by viewModel<TrailersViewModel>()

    private var _binding: FragmentTrailersBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = arguments?.getString(ARGS_MOVIE_ID)
        Log.e("MidTrailer", "$movieId")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrailersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTrailer(movieId!!)

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun render(state: TrailerState) {
        when (state) {
            is TrailerState.Content -> showTrailer(state.trailer.linkEmbed)
            is TrailerState.Error -> showError(state.message)
        }
    }

    private fun showTrailer(trailerUrl: String) {
        binding.errorSearchTrailer.visibility = View.GONE
        binding.webView.visibility = View.VISIBLE
        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        Log.e("webView", trailerUrl)
        binding.webView.loadUrl(trailerUrl)
    }

    private fun showError(message: String) {
        binding.webView.visibility = View.GONE
        val errorMessage = binding.errorSearchTrailer
        errorMessage.visibility =View.VISIBLE
        errorMessage.text = message
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}