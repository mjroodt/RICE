package com.rice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registration.*

/**
 * A Login Form Example in Kotlin Android
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get reference to all views

        btn_reset.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            et_user_name.setText("")
            et_password.setText("")


        }

        tvRegister.setOnClickListener {

            startActivity(Intent(this, RegistrationActivity::class.java));
            // start your next activity
        }

        var firebaseAuth :FirebaseAuth = FirebaseAuth.getInstance();
        val user: FirebaseUser? = firebaseAuth.currentUser;

        if(user != null){
            finish();
            startActivity(Intent(this, IceListActivity::class.java));
        }

        // set on-click listener
       btn_submit.setOnClickListener {
            showMessage("Authenticating...")
           firebaseAuth.signInWithEmailAndPassword(et_user_name.text.toString().trim(), et_password.text.toString().trim()).addOnCompleteListener({ task ->
                if(task.isSuccessful){
                    var intent = Intent(this, IceListActivity::class.java)
                    startActivity(intent)

                }else{
                    showMessage("Error: ${task.exception?.message}",Toast.LENGTH_LONG)
                }
            })

        }
    }


   fun showMessage(message: String,toast: Int= Toast.LENGTH_SHORT)
    {
        Toast.makeText(this,message,toast).show();
    }

}