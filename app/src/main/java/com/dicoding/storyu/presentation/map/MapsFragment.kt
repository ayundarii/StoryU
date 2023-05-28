package com.dicoding.storyu.presentation.map

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dicoding.storyu.R
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class MapsFragment : Fragment() {

    private lateinit var binding: FragmentMapsBinding
    private val viewModel: MapsViewModel by inject()
    private val listLocations = ArrayList<LatLng>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("PotentialBehaviorOverride")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync { googleMap ->
            setMapStyle(googleMap)
            getMyLocation(googleMap)

            viewModel.getStories()
            viewModel.storyResult.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        listLocations.clear()
                        for (story in response.data) {
                            if (story.latitude != null && story.longitude != null) {
                                Timber.d("lat : ${story.latitude} and long : ${story.longitude} by ${story.name}")
                                val location =
                                    LatLng(story.latitude.toDouble(), story.longitude.toDouble())
                                listLocations.add(location)
                                val markerOptions =
                                    MarkerOptions().position(location).title(story.name)
                                        .snippet(story.description)
                                Timber.d("MarkerOptions : $markerOptions")
                                googleMap.addMarker(markerOptions)
                            }
                        }

                        if (listLocations.isNotEmpty()) {
                            Timber.d("List location size: ${listLocations.size}")
                            val boundsBuilder = LatLngBounds.builder()
                            for (location in listLocations) {
                                boundsBuilder.include(location)
                            }
                            val bounds = boundsBuilder.build()
                            val padding = 500
                            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                            googleMap.animateCamera(cameraUpdate)
                        }
                    }

                    else -> {}
                }
            }

            googleMap.uiSettings.apply {
                isZoomControlsEnabled = true
                isIndoorLevelPickerEnabled = true
                isCompassEnabled = true
                isMapToolbarEnabled = true
            }
        }
    }


    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                val mapFragment =
                    childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                mapFragment?.getMapAsync { googleMap ->
                    getMyLocation(googleMap)
                }
            }
        }

    private fun getMyLocation(googleMap: GoogleMap) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun setMapStyle(googleMap: GoogleMap) {
        try {
            val success =
                googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        requireContext(),
                        R.raw.story_map
                    )
                )
            if (!success) {
                Timber.tag("Fragment").e("Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Timber.tag("Fragment").e(exception, "Can't find style. Error: ")
        }
    }
}