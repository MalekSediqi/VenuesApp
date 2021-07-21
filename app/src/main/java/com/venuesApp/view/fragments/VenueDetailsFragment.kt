package com.venuesApp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.venuesApp.databinding.FragmentVenueDetailsBinding
import com.venuesApp.utils.Resource
import com.venuesApp.viewmodel.VenuesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VenueDetailsFragment : Fragment() {

    private var _binding: FragmentVenueDetailsBinding? = null
    private val binding get() = _binding!!
    private val venuesViewModel: VenuesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVenueDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        venuesViewModel.selectedValue.observe(viewLifecycleOwner, {
            venuesViewModel.venueDetails(it.id).observe(viewLifecycleOwner, {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        binding.venueDetailsTitle.text = it.data?.name
                        binding.venueDetailsDesc.text = it.data?.description
                    }
                    Resource.Status.LOADING -> {

                    }
                    Resource.Status.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                }

            })
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}