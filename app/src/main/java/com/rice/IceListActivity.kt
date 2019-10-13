package com.rice
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.FirebaseFirestoreException
import com.rice.model.Ice
import com.rice.viewholder.IceViewHolder
import kotlinx.android.synthetic.main.activity_ice_list.*
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class IceListActivity : AppCompatActivity() {

    private val TAG = "IceListActivity"

    private var adapter: FirestoreRecyclerAdapter<Ice, IceViewHolder>? = null

    private var firestoreDB: FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null
    private var iceList = mutableListOf<Ice>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ice_list)

        firestoreDB = FirebaseFirestore.getInstance()

        val mLayoutManager = LinearLayoutManager(applicationContext)
        rvIceList.layoutManager = mLayoutManager
        rvIceList.itemAnimator = DefaultItemAnimator()

        loadIceList()

        firestoreListener = firestoreDB!!.collection("Ice")
            .addSnapshotListener(EventListener { documentSnapshots, e ->
                if (e != null) {
                    Log.e(TAG, "Listen failed!", e)
                    return@EventListener
                }

                iceList = mutableListOf()

                for (doc in documentSnapshots!!) {
                    val ice = doc.toObject(Ice::class.java)
                    ice.id = doc.id
                    iceList.add(ice)
                }

                adapter!!.notifyDataSetChanged()
                rvIceList.adapter = adapter
            })
    }

    override fun onDestroy() {
        super.onDestroy()

        firestoreListener!!.remove()
    }

    private fun loadIceList() {

        val query = firestoreDB!!.collection("Ice")

        val response = FirestoreRecyclerOptions.Builder<Ice>()
            .setQuery(query, Ice::class.java)
            .build()

        adapter = object : FirestoreRecyclerAdapter<Ice, IceViewHolder>(response) {
            override fun onBindViewHolder(holder: IceViewHolder, position: Int, model: Ice) {
                val ice = iceList[position]

                holder.firstName.text = ice.firstName
                holder.lastName.text = ice.lastName

                holder.edit.setOnClickListener { updateNote(ice) }

                holder.delete.setOnClickListener { deleteNote(ice.id!!) }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IceViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_ice, parent, false)

                return IceViewHolder(view)
            }

        }

        adapter!!.notifyDataSetChanged()
        rvIceList.adapter = adapter
    }

    public override fun onStart() {
        super.onStart()

        adapter!!.startListening()
    }

    public override fun onStop() {
        super.onStop()

        adapter!!.stopListening()
    }

    private fun updateNote(note: Ice) {
        val intent = Intent(this, IceActivity::class.java)
        intent.putExtra("UpdateIcId", note.id)
        intent.putExtra("UpdateIceFirstName", note.firstName)
        intent.putExtra("UpdateIceLastName", note.lastName)
        startActivity(intent)
    }

    private fun deleteNote(id: String) {
        firestoreDB!!.collection("notes")
            .document(id)
            .delete()
            .addOnCompleteListener {
                Toast.makeText(applicationContext, "Note has been deleted!", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.ricemenu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            if (item.itemId == R.id.addIce) {
                val intent = Intent(this, IceActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
