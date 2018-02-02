package com.sixt.pdfviewersample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sixt.pdfviewer.PdfView
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fileName = "sample1.pdf"
        val pdfFile = File(cacheDir, fileName)
        IOUtils.copy(assets.open(fileName), FileOutputStream(pdfFile))

        val pdfView = findViewById<PdfView>(R.id.pdfView)
        pdfView.loadPdf(pdfFile)
    }
}
