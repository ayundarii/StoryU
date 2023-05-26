package com.dicoding.storyu.presentation.add_story

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.dicoding.storyu.base.BaseFragment
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.databinding.FragmentAddStoryBinding
import com.dicoding.storyu.utils.constant.createCustomTempFile
import com.dicoding.storyu.utils.constant.uriToFile
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.io.File

class AddStoryFragment : BaseFragment<FragmentAddStoryBinding>(), ActivityCompat.OnRequestPermissionsResultCallback {

    private val viewModel: AddStoryViewModel by inject()
    private lateinit var currentPhotoPath: String
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
                navigateToHome()
            }

            binding.btnCamera.setOnClickListener {
                startTakePhoto()
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
        val permissions = REQUIRED_PERMISSIONS
        permissions.any {
            ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), it)
        }

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                val allGranted = permissions.all { it.value }
                if (allGranted) {
                    binding.root.showSnackBar("Permission granted.")
                } else {
                    if (!permissions.entries.all {
                            ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), it.key)
                    }) {
                        binding.root.showSnackBar("Required permissions not granted.")
                    } else {
                        binding.root.showSnackBar("Required permissions not granted.")
                    }
                }
            }

        requestPermissionLauncher.launch(permissions)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        launcherIntentCamera.launch(intent)

        createCustomTempFile(requireActivity().application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                AUTHORITY,
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
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

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            myFile.let { file ->
                getFile = file
                binding.imgAddStory.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    override fun initProcess() {

    }

    override fun initObservers() {
    }

    private fun navigateToHome() {
        findNavController().navigate(
            AddStoryFragmentDirections.actionAddStoryFragmentToHomeFragment()
        )
    }


    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE)
        private const val AUTHORITY = "com.dicoding.storyu"
    }

}