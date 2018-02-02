package com.sixt.pdfviewer

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.support.v7.widget.RecyclerView
import android.util.LruCache
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

open class PdfAdapter(private val pdfRenderer: PdfRenderer) : RecyclerView.Adapter<PdfAdapter.ViewHolder>() {

    private lateinit var memoryCache: LruCache<Int, Bitmap>

    init {
        initMemoryCache()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.findViewById(R.id.pdfPageImage)
    }

    override fun getItemViewType(position: Int): Int = R.layout.pdf_page

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var bitmap = getBitmapFromMemCache(position)
        if (bitmap == null) {
            val page = pdfRenderer.openPage(position)
            bitmap = Bitmap.createBitmap(2 * page.width,2 * page.height, Bitmap.Config.ARGB_8888)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()
            addBitmapToMemoryCache(position, bitmap!!)
        }
        holder.imageView.setImageBitmap(bitmap)
    }

    override fun getItemCount(): Int {
        return pdfRenderer.pageCount
    }

    private fun addBitmapToMemoryCache(key: Int, bitmap: Bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap)
        }
    }

    private fun getBitmapFromMemCache(key: Int): Bitmap? {
        return memoryCache.get(key)
    }

    private fun initMemoryCache() {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

        val cacheSize = maxMemory / 8

        memoryCache = object : LruCache<Int, Bitmap>(cacheSize) {
            override fun sizeOf(key: Int, bitmap: Bitmap): Int {
                return bitmap.byteCount / 1024
            }
        }
    }
}