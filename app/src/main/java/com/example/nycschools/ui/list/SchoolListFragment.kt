package com.example.nycschools.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nycschools.R
import com.example.nycschools.model.School
import com.example.nycschools.ui.school.SchoolDetailFragment

/**
 * Shows the list of schools
 */
class SchoolListFragment : Fragment() {

    companion object {
        fun newInstance() = SchoolListFragment()
    }

    /**
     * We want to delay the creation of the viewModel until after onActivityCreated
     */
    private val viewModel: SchoolListViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProvider(
            this,
            SchoolListViewModel.Factory(activity.application)
        )[SchoolListViewModel::class.java]
    }
    private var schoolRecyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_school_list, container, false)
        schoolRecyclerView = view.findViewById(R.id.school_recyclerview)
        schoolRecyclerView?.layoutManager = LinearLayoutManager(context)

        // We observe the viewmodel.schoolList and update the ui accordingly
        val schoolObserver = Observer<List<School>> { newList ->
            schoolRecyclerView?.adapter = SchoolAdapter(newList) {
                goToDetailFragment(it)
            }
        }
        viewModel.schoolList.observe(viewLifecycleOwner, schoolObserver)
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
        return view
    }

    /**
     * Used to exchange the current fragment for the detail fragment
     *
     * @param school The item that was selected by the user from the list of schools
     */
    private fun goToDetailFragment(school: School){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, SchoolDetailFragment(school))
            .commitNow()
    }

    /**
     * Method for displaying a Toast error message for network errors.
     */
    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}