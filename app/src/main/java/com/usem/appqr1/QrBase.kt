package com.usem.appqr1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.usem.appqr1.databinding.ActivityQrBaseBinding

class QrBase : AppCompatActivity() {

    private lateinit var binding: ActivityQrBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonscan.setOnClickListener { initScanner() }
        binding.buttonbuscar.setOnClickListener { goToDatosQr() }
    }

    private fun initScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Coloca el código en el recuadro")
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    private fun goToDatosQr() {
        val intent = Intent(this, DatosQr::class.java)
        startActivity(intent)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
            } else {
                // Actualiza el TextView con el valor escaneado
                binding.textResult.text = "No. Serie ${result.contents}"
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
