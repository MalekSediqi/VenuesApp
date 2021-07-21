package com.venuesApp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.venuesApp.databinding.FragmentVenuesBinding
import com.venuesApp.utils.Resource
import com.venuesApp.utils.networkStatus
import com.venuesApp.view.adapters.VenuesAdapter
import com.venuesApp.viewmodel.VenuesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.beryukhov.reactivenetwork.ReactiveNetwork

@AndroidEntryPoint
class VenuesFragment : Fragment() {

    private var _binding: FragmentVenuesBinding? = null
    private val venuesViewModel: VenuesViewModel by activityViewModels()
    private val binding get() = _binding!!
    private lateinit var searchVenueByTitle: SearchView
    private val venuesAdapter: VenuesAdapter = VenuesAdapter(mutableListOf()) {
        venuesViewModel.selectVenue(it)
    }

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
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = venuesAdapter
        searchListener(venuesAdapter)
        venuesViewModel._venues.observe(viewLifecycleOwner, {
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
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    override fun onResume() {
        super.onResume()
        context?.let {
            ReactiveNetwork().observeNetworkConnectivity(it.applicationContext).onEach {
                if (it.state.name == "DISCONNECTED") {
                    networkStatus = false
                    venuesViewModel.setNetworkStatus(false)
                } else if (it.state.name == "CONNECTED") {
                    networkStatus = true
                    venuesViewModel.setNetworkStatus(true)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private fun searchListener(adapter: VenuesAdapter) {
        searchVenueByTitle = binding.searchVenue
        searchVenueByTitle.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                venuesViewModel.getVenuesByTitle(p0 ?: "").observe(viewLifecycleOwner, {
                    adapter.setVenues(it ?: emptyList())
                })
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}