package com.sixt.pdfviewer

import android.content.Context
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.widget.FrameLayout
import java.io.File

open class PdfView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var fileDescriptor: ParcelFileDescriptor

    private lateinit var pdfRenderer: PdfRenderer

    private lateinit var adapter: PdfAdapter

    private val recyclerView: RecyclerView = RecyclerView(context)

    init {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context.resources.getDimensionPixelSize(R.dimen.two), 1))
    }

    fun loadPdf(file: File) {
        fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        pdfRenderer = PdfRenderer(fileDescriptor);
        adapter = PdfAdapter(pdfRenderer)
        recyclerView.adapter = adapter
        addView(recyclerView)
        adapter.notifyDataSetChanged()
    }
}