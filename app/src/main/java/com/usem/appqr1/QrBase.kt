package com.usem.appqr1

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import com.usem.appqr1.databinding.ActivityQrBaseBinding


class QrBase : AppCompatActivity() {


    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                showCamera()
            } else {
                // Manejar el caso en el que el permiso no fue concedido
            }
        }
    private val scanLauncher =
        registerForActivityResult(ScanContract()) {
                result: ScanIntentResult -> {
            if(result.contents == null) {
                Toast.makeText(this,"Cancelled",Toast.LENGTH_SHORT).show()
            }else {
                setResult(result.contents)
            }
        }
        }

    private lateinit var binding: ActivityQrBaseBinding
    private fun setResult(string: String) {
        binding.textResult.text = string
    }

    private fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan QR code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(false)

        scanLauncher.launch(options)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initBinding()
        initViews()

        // Configura el layout
        setContentView(binding.root)

        setContentView(R.layout.activity_qr_base)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews() {
        binding.fab.setOnClickListener {
            checkPermissionCamera(this)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermissionCamera(context: Context) {
        val permission = android.Manifest.permission.CAMERA
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {

            showCamera()
        } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            Toast.makeText(context, "CAMERA permission required", Toast.LENGTH_SHORT).show()
        }
        else {
            // Solicitar permiso
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    private fun initBinding() {
        binding = ActivityQrBaseBinding.inflate(layoutInflater)
    }
}

