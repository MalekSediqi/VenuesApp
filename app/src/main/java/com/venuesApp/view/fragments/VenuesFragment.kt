package com.venuesApp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.venuesApp.databinding.FragmentVenuesBinding
import com.venuesApp.utils.Resource
import com.venuesApp.view.adapters.VenuesAdapter
import com.venuesApp.viewmodel.VenuesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VenuesFragment : Fragment() {

    private var _binding: FragmentVenuesBinding? = null
    private val venuesViewModel: VenuesViewModel by activityViewModels()
    private val binding get() = _binding!!
    private val venuesAdapter:VenuesAdapter = VenuesAdapter(mutableListOf(),{})
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentVenuesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.venuesRecyclerView
        progressBar = binding.loadingVenues
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = venuesAdapter
        venuesViewModel._venues.observe(viewLifecycleOwner,{
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    venuesAdapter.setVenues(it.data ?: emptyList())
                    recyclerView.visibility = View.VISIBLE
                }
                Resource.Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Resource.Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}