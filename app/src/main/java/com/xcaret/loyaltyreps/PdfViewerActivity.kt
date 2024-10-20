package com.xcaret.loyaltyreps

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import com.xcaret.loyaltyreps.databinding.ActivityLoadPdfBinding
import com.xcaret.loyaltyreps.view.general.vm.GeneralViewModel


class PdfViewerActivity() : AppCompatActivity(){
//    val _viewModel: GeneralViewModel by viewModels()
    lateinit var binding: ActivityLoadPdfBinding


//    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityLoadPdfBinding.inflate(layoutInflater)
//        val view = binding.root
//        val pdfName = intent.extras!!.getString("file_name", "default")!!
//        val pdflink = intent.extras!!.getString("file_url", "https://pdfobject.com/pdf/sample.pdf")
//        supportActionBar!!.title = resources.getString(R.string.go_back)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//
//        binding.pdfContainer.settings.javaScriptEnabled = true
//        binding.pdfContainer.settings.allowFileAccess = true
//        binding.pdfContainer.loadUrl("https://docs.google.com/gview?embedded=true&url=$pdflink")
//
//        binding.downLoadPdfSelected.setOnClickListener {
//            _viewModel.downloadPdf(pdfName, pdflink) { uri, error ->
//                uri?.let {
//                    Toast.makeText(this, "PDF downloading", Toast.LENGTH_SHORT).show()
//                } ?: run {
//                    Toast.makeText(this, "PDF $error", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
    }

}


