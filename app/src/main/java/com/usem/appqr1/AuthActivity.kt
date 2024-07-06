package com.usem.appqr1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.usem.appqr1.databinding.ActivityAuthBinding
import com.usem.appqr1.databinding.ActivityQrBaseBinding
import com.google.firebase.auth.auth

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.loginbtn.setOnClickListener{
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if(email.isEmpty()){
                binding.email.error = "Ingresa un correo valido"
                return@setOnClickListener
            }
            if(password.isEmpty()){
                binding.password.error = "Ingrese una contraseña"
                return@setOnClickListener
            }

            singIn(email,password)
        }
    }

    private fun singIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(this,"Listo",Toast.LENGTH_LONG).show()
                    val intent = Intent(this,QrBase :: class.java)
                    startActivity(intent)

                }else{
                    Toast.makeText(this,"ERROR",Toast.LENGTH_SHORT).show()
                }

            }

    }

}