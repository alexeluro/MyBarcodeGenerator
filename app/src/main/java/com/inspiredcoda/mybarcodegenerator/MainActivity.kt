package com.inspiredcoda.mybarcodegenerator

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.inspiredcoda.mybarcodegenerator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.barcodeBtn.setOnClickListener {
            if (validateInput()) {
                generateBarcode(binding!!.barcodeEdt.text.toString())
            }
        }

    }

    private fun validateInput(): Boolean {
        if (binding!!.barcodeEdt.text == null || binding!!.barcodeEdt.text.toString() == ""){
            binding!!.barcodeEdt.error = "Content cannot be empty"
            return false
        }else{
            binding!!.barcodeEdt.error = null
        }
        return true
    }

    private fun generateBarcode(content: String) {
        try {
            val bitMatrix = MultiFormatWriter().encode(
                content,
                BarcodeFormat.CODE_128,
                binding!!.barcodeImg.width,
                binding!!.barcodeImg.height
            )

            val bitmap = Bitmap.createBitmap(
                binding!!.barcodeImg.width,
                binding!!.barcodeImg.height,
                Bitmap.Config.ARGB_8888
            )

            for (x in 0 until bitmap.width) {
                for (y in 0 until bitmap.height) {
                    val color = if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE
                    bitmap.setPixel(x, y, color)
                }
            }

            binding!!.barcodeImg.setImageBitmap(bitmap)

        } catch (ex: WriterException) {
            ex.printStackTrace()
        }
    }

}