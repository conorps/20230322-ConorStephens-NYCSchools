package com.example.nycschools.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nycschools.R
import com.example.nycschools.model.School

class SchoolAdapter(var schoolList: List<School>, private val onItemClicked: (School) -> Unit) :
    RecyclerView.Adapter<SchoolViewHolder>() {

    override fun getItemCount(): Int {
        return schoolList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.school_item,
            parent, false
        )
        return SchoolViewHolder(view)
    }

    /**
     * The onItemClicked function will be used to send the user to the detail fragment
     */
    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        val school = schoolList[position]
        holder.bind(school)
        holder.itemView.setOnClickListener {
            onItemClicked(school)
        }
    }
}