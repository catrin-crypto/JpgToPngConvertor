package com.example.jpgtopngconvertor.presentation.converter

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.jpgtopngconvertor.R
import by.kirich1409.viewbindingdelegate.viewBinding
import moxy.MvpAppCompatFragment
import com.example.jpgtopngconvertor.R.layout.fragment_converter
import com.example.jpgtopngconvertor.click
import com.example.jpgtopngconvertor.data.converter.ConverterFactory
import com.example.jpgtopngconvertor.databinding.FragmentConverterBinding
import com.example.jpgtopngconvertor.presentation.scheduler.SchedulersFactory
import moxy.ktx.moxyPresenter

const val REQUEST_CODE = 1

class ConverterFragment : MvpAppCompatFragment(fragment_converter), ConverterView {

    companion object {
        fun newInstance(): Fragment = ConverterFragment()
    }

    private val presenter by moxyPresenter {
        ConverterPresenter(
            converter = ConverterFactory.create(requireContext()),
            schedulers = SchedulersFactory.create()
        )
    }

    private val viewBinding: FragmentConverterBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.convertBtn.click(::pickImage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.data?.let(presenter::convert)
            ?: Toast.makeText(
                requireContext(),
                getString(R.string.Image_is_not_selected),
                Toast.LENGTH_SHORT
            ).show()
    }

    override fun showLoading() {
        viewBinding.convertBtn.click(presenter::cancel)
        viewBinding.convertBtn.text = getString(R.string.Cancel)
    }

    override fun showContent(uri: Uri?) {
        val bitmap: Bitmap? = uri?.let {
            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, it)
        }
        viewBinding.photoContainer.setImageBitmap(bitmap)
        viewBinding.convertBtn.text = getString(R.string.Pick_image)
        viewBinding.convertBtn.click(::pickImage)
    }

    private fun pickImage() {
        val getIntent = Intent(Intent.ACTION_GET_CONTENT)
        getIntent.type = "image/*"
        startActivityForResult(getIntent, REQUEST_CODE)
    }

    override fun showError(error: Throwable) {
        Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
    }
}