package com.xcaret.loyaltyreps.view.general.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.xcaret.loyaltyreps.databinding.ActivityLoadPdfBinding
import com.xcaret.loyaltyreps.databinding.FragmentGalleryBinding
import com.xcaret.loyaltyreps.view.base.BaseFragmentDataBinding
import com.xcaret.loyaltyreps.view.general.vm.GeneralViewModel

class PdfViewerFragment (): BaseFragmentDataBinding<ActivityLoadPdfBinding>(){
    val _viewModel: GeneralViewModel by viewModels()
    override val tagForBar: String
        get() = "PdfViewerFragment"

    override fun setHeaderFragment() {}

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBinding(ActivityLoadPdfBinding.inflate(inflater))

        val pdfName = arguments?.getString("file_name", "default")!!
        val pdflink = arguments?.getString("file_url", "https://pdfobject.com/pdf/sample.pdf")

        Log.i(tagForBar, "onCreateView: $pdflink")

        binding.pdfContainer.settings.javaScriptEnabled = true
        binding.pdfContainer.settings.allowFileAccess = true
        binding.pdfContainer.loadUrl("https://docs.google.com/gview?embedded=true&url=$pdflink")

        binding.downLoadPdfSelected.setOnClickListener {
            if (pdflink != null) {
                _viewModel.downloadPdf(pdfName, pdflink, requireActivity()) {
                    Toast.makeText(requireContext(), "PDF downloading", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }
}