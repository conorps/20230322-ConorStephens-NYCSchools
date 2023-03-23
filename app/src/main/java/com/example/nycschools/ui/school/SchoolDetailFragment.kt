package com.example.nycschools.ui.school

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import com.example.nycschools.R
import com.example.nycschools.model.School
import com.example.nycschools.model.TestScores
import com.example.nycschools.ui.list.SchoolListFragment

class SchoolDetailFragment(school: School) : Fragment() {

    private val viewModel: SchoolDetailViewModel by lazy {
        ViewModelProvider(
            this,
            SchoolDetailViewModel.Factory(school)
        )[SchoolDetailViewModel::class.java]
    }
    private var schoolTextView: TextView? = null
    private var mathScoreTextView: TextView? = null
    private var readingScoreTextView: TextView? = null
    private var writingScoreTextView: TextView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            goToListFragment()
        }
        val view = inflater.inflate(R.layout.fragment_school_detail, container, false)
        schoolTextView = view.findViewById(R.id.detail_school_name_textView)
        mathScoreTextView = view.findViewById(R.id.detail_math_score_textView)
        readingScoreTextView = view.findViewById(R.id.detail_reading_score_textView)
        writingScoreTextView = view.findViewById(R.id.detail_writing_score_textView)
        val testScoreObserver = Observer<TestScores> {
            initTextViews()
        }
        viewModel.testScores.observe(viewLifecycleOwner, testScoreObserver)
        return view
    }

    /**
     * I've overridden onBackPressed so that this function returns the user to the List fragment
     * Given more time, or a larger project, I would've worked with the jetpack navigation component
     * and in this case added up navigation to the toolbar
     */
    private fun goToListFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, SchoolListFragment())
            .commitNow()
    }

    /**
     * Initializing the textViews after the observer has indicated that the call to fetch test
     * scores has finished
     */
    private fun initTextViews() {
        schoolTextView?.text = viewModel.testScores.value?.school_name ?: "--"
        readingScoreTextView?.text = getString(
            R.string.critical_reading_text,
            viewModel.testScores.value?.sat_critical_reading_avg_score
        )
        writingScoreTextView?.text =
            getString(R.string.writing_text, viewModel.testScores.value?.sat_writing_avg_score)
        mathScoreTextView?.text =
            getString(R.string.math_text, viewModel.testScores.value?.sat_math_avg_score)
    }
}