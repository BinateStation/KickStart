package com.binatestation.kickstart.ui.main.photo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.binatestation.android.kickoff.utils.fragments.ImageCarouselFragment
import kotlinx.coroutines.ObsoleteCoroutinesApi

/**
 * A simple [Fragment] subclass.
 */
class ImageCarouselDemoFragment : ImageCarouselFragment() {

    private lateinit var photoViewModel: PhotoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoViewModel = ViewModelProvider(this)[PhotoViewModel::class.java]
    }

    @ObsoleteCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoViewModel.photos.observe(viewLifecycleOwner, { it ->
            it.data?.let { photoModels ->
                setImages(photoModels.subList(0, 5).map { it.thumbnailUrl ?: "" })
                startAutoScroll()
            }
        })
    }
}
