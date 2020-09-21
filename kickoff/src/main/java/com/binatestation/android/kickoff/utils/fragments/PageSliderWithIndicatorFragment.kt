package com.binatestation.android.kickoff.utils.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.binatestation.android.kickoff.R


/**
 * A simple [Fragment] subclass. which can be user for page slider with indicator dots at the bottom.
 */
open class PageSliderWithIndicatorFragment : TabLayoutFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page_slider_with_indicator, container, false)
    }

    fun addFragment(fragment: Fragment) {
        addFragment(fragment, getString(R.string.bullet_point))
    }
}