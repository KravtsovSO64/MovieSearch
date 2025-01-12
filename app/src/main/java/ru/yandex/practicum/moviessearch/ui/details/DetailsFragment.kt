package ru.yandex.practicum.moviessearch.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ru.yandex.practicum.moviessearch.R
import ru.yandex.practicum.moviessearch.databinding.FragmentDetailsBinding

class DetailsFragment: Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var tabsMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = DetailsViewPagerAdapter(childFragmentManager,
            lifecycle, arguments?.getString(ARGS_POSTER_URL)!!, arguments?.getString(ARGS_MOVIE_ID)!!)

        tabsMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.poster)
                1 -> tab.text = getString(R.string.details)
            }
        }
        tabsMediator.attach()
    }

    override fun onDestroyView() {
        tabsMediator.detach()
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val ARGS_POSTER_URL = "poster"
        private const val ARGS_MOVIE_ID = "id"

        const val TAG = "DetailsFragment"

        fun newInstance(poster: String, id: String): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle().apply {
                putString(ARGS_POSTER_URL, poster)
                putString(ARGS_MOVIE_ID, id)
            }
            fragment.arguments = args
            return  fragment
        }
    }
}