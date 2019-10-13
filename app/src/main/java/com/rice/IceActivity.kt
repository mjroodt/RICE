package com.rice

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.rice.model.Ice
import kotlinx.android.synthetic.main.activity_ice.*

class IceActivity: AppCompatActivity() {

    private val TAG = "AddIceActivity"

    private var firestoreDB: FirebaseFirestore? = null
    internal var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ice)

        firestoreDB = FirebaseFirestore.getInstance()

        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getString("UpdateIceId")

            edFirstName.setText(bundle.getString("UpdateIceFirstName"))
            edLastName.setText(bundle.getString("UpdateIceLastName"))
        }

        btAdd.setOnClickListener {
            val firstName = edFirstName.text.toString()
            val lastName = edLastName.text.toString()

            if (firstName.isNotEmpty()) {
                if (id.isNotEmpty()) {
                    updateIce(id, firstName, lastName)
                } else {
                    addIce(firstName, lastName)
                }
            }

            finish()
        }
    }

    private fun updateIce(id: String, firstName: String, lastName: String) {
        val ice = Ice(id, firstName, lastName).toMap()

        firestoreDB!!.collection("ices")
                .document(id)
                .set(ice)
                .addOnSuccessListener {
                    Log.e(TAG, "ICE document update successful!")
                    Toast.makeText(applicationContext, "ICE has been updated!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error adding ICE document", e)
                    Toast.makeText(applicationContext, "Note could not be updated!", Toast.LENGTH_SHORT).show()
                }
    }

    private fun addIce(firstName: String, lastName: String) {
        val ice = Ice(firstName, lastName ).toMap()

        firestoreDB!!.collection("Ice")
                .add(ice)
                .addOnSuccessListener { documentReference ->
                    Log.e(TAG, "DocumentSnapshot written with ID: " + documentReference.id)
                    Toast.makeText(applicationContext, "ICE has been added!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error adding ICE document", e)
                    Toast.makeText(applicationContext, "ICE could not be added!", Toast.LENGTH_SHORT).show()
                }
    }
}
