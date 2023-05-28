package com.dicoding.storyu.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dicoding.storyu.R
import com.dicoding.storyu.base.custom_view.CustomLoadingDialog
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!
    private lateinit var loadingDialog: CustomLoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container, savedInstanceState)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = CustomLoadingDialog(requireContext())
        initIntent()
        initUI()
        initActions()
        initProcess()
        initObservers()
    }

    abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): VB

    abstract fun initUI()

    abstract fun initProcess()

    abstract fun initObservers()

    protected open fun initIntent() {}

    protected open fun initActions() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun View.showSnackBar(message: String) {
        Snackbar.make(
            this,
            message,
            Snackbar.LENGTH_LONG
        ).also { snackbar ->
            snackbar.setAction("OK") {
                snackbar.dismiss()
            }
        }.show()
    }

    open fun showLoadingDialog() {
        loadingDialog.showDialog()
    }
    open fun hideLoadingDialog() {
        loadingDialog.dismissDialog()
    }

    private fun isMainPage(currentDestination: BaseFragment<VB>): Boolean {
        return currentDestination.id == R.id.mapsFragment
    }

}