package com.dicoding.storyu.presentation.add_story

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dicoding.storyu.base.BaseFragment
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.databinding.FragmentAddStoryBinding
import com.dicoding.storyu.presentation.home.HomeFragmentDirections
import com.dicoding.storyu.utils.constant.uriToFile
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.io.File

class AddStoryFragment : BaseFragment<FragmentAddStoryBinding>(), ActivityCompat.OnRequestPermissionsResultCallback {

    private val viewModel: AddStoryViewModel by inject()

    private var getFile: File? = null
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAddStoryBinding {
        return FragmentAddStoryBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        if (!allPermissionsGranted()) {
            requestPermissions()
        }

        binding.apply {
            toolBar.setNavigationOnClickListener {

            }

            binding.btnCamera.setOnClickListener {
                binding.root.showSnackBar("Cannot do this yet.")
            }

            binding.btnGallery.setOnClickListener {
                startGallery()
            }

            binding.btnUpload.setOnClickListener {
                if (getFile != null) {
                    val file = getFile as File
                    val description = edAddDescription.text.toString()
                    viewModel.addStory(
                        file,
                        description,
                        null,
                        null
                    )
                } else {
                    binding.root.showSnackBar("Please make sure you have chose a picture.")
                }
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        val permissions = Manifest.permission.READ_EXTERNAL_STORAGE
        val requestCode = REQUEST_CODE_PERMISSIONS
        val shouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            requireActivity(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                startGallery()
            } else {
                if (!shouldShowRequestPermissionRationale) {
                    binding.root.showSnackBar("Request permission not granted.")
                } else {
                    binding.root.showSnackBar("Request permission not granted.")
                }
            }
        }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            binding.root.showSnackBar("Permission granted.")
        } else {
            requestPermissionLauncher.launch(permissions)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri

            selectedImg.let { uri ->
                val myFile = uriToFile(uri, requireContext())
                getFile = myFile
                binding.imgAddStory.setImageURI(uri)
            }
        }
    }

    override fun initProcess() {

    }

    override fun initObservers() {
        viewModel.addStoryResult.observe(viewLifecycleOwner) {response ->
            Timber.d("Response is $response")
            when (response) {
                is ApiResponse.Success -> {
                    navigateToHome()
                }

                is ApiResponse.Error -> {
                    binding.root.showSnackBar(response.errorMessage)
                }

                is ApiResponse.Loading -> {

                }

                is ApiResponse.Empty -> {}
            }
        }

    }

    private fun navigateToHome() {
        findNavController().navigate(
            AddStoryFragmentDirections.actionAddStoryFragmentToHomeFragment()
        )
    }


    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

}