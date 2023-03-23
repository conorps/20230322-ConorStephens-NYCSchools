package com.example.nycschools.ui.list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nycschools.R
import com.example.nycschools.model.School

class SchoolViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    var school: School? = null

    val nameTextView: TextView = itemView.findViewById(R.id.card_name_textView)
    val boroTextView: TextView = itemView.findViewById(R.id.card_boro_textView)

    fun bind(school: School) {
        this.school = school
        nameTextView.text = school.school_name
        boroTextView.text = school.getFullBoro()
    }

    /**
     * The click is handled by the method passed into the adapter
     */
    override fun onClick(v: View) {
    }
}