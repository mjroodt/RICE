package com.rice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registration.*


class RegistrationActivity : AppCompatActivity() {

    lateinit var firebaseAuth : FirebaseAuth;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        firebaseAuth =FirebaseAuth.getInstance();


        btnBackToMain.setOnClickListener{

            startActivity(Intent(this, MainActivity::class.java));

        }


        userLogin.setOnClickListener {
            showMessage("Registering...");
            if (validate()) {

                val password = userPassword.text.toString().trim();
                val email = userEmail.text.toString().trim();


                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{task ->

                    if(task.isSuccessful){
                        Toast.makeText(this,"Registration Successful",Toast.LENGTH_SHORT).show();
                        startActivity(Intent(this, IceActivity::class.java));
                    }else{
                        Toast.makeText(this,"Registration Failed",Toast.LENGTH_SHORT).show();
                    }

            }

        }

        userLogin.setOnClickListener {

            startActivity(Intent(this, MainActivity::class.java));
        }
        }
    }

    fun showMessage(message: String)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


    fun validate(): Boolean{
        var result = false;
        val name = userName.text;
        val password = userPassword.text;
        val email = userEmail.text;

        if(name.isEmpty() || password.isEmpty() || email.isEmpty()){
           Toast.makeText(this,"Please enter all the details",Toast.LENGTH_SHORT).show();
        }else{
            result = true;

        }
        return result;
    }

}
