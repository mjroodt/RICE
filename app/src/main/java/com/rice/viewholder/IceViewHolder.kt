package com.rice.viewholder
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rice.R
class IceViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var firstName: TextView
    var lastName: TextView
    var edit: ImageView
    var delete: ImageView

    init {
        firstName = view.findViewById(R.id.tvFirstName)
        lastName = view.findViewById(R.id.tvLastName)

        edit = view.findViewById(R.id.ivEdit)
        delete = view.findViewById(R.id.ivDelete)
    }
}