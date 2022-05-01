package com.nlv2022.trenaghorik.Fragments

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.nlv2022.trenaghorik.R
import com.nlv2022.trenaghorik.adapters.DayModel
import com.nlv2022.trenaghorik.adapters.DaysAdapter
import com.nlv2022.trenaghorik.adapters.ExerciseAdapter
import com.nlv2022.trenaghorik.adapters.ExerciseModel
import com.nlv2022.trenaghorik.databinding.ExercisesListFragmentBinding
import com.nlv2022.trenaghorik.databinding.FragmentDaysBinding
import com.nlv2022.trenaghorik.utils.FragmentManager
import com.nlv2022.trenaghorik.utils.MainViewModel

class ExercisesListFragment : Fragment() {
    private lateinit var binding: ExercisesListFragmentBinding
    private lateinit var adapter: ExerciseAdapter
    private var ab: ActionBar? = null

    private val model: MainViewModel by activityViewModels() //13


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExercisesListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    } // создание фрагмента. Переопределение интерфейса

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        model.mutableListExercise.observe(viewLifecycleOwner) {

            for (i in 0 until model.getExerciseCount()) {
                it[i] = it[i].copy(isDone = true)

            } //от нуля до количества выполненных упражнений

            adapter.submitList(it)
        }

    }


    private fun init() = with(binding) {
        ab = (activity as AppCompatActivity).supportActionBar
        adapter = ExerciseAdapter()

        val count = model.getExerciseCount()

        val title = getString(R.string.exercises) + ": " + count + " / ${getAllExercise()}"
        ab?.title = title



        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter
        bStart.setOnClickListener {
            FragmentManager.setFragment(
                WaitingFragment.newInstance(),
                activity as AppCompatActivity
            )
        }

    }


    companion object {

        @JvmStatic
        fun newInstance() = ExercisesListFragment()
    }

    private fun getAllExercise(): Int {
        val exArray = resources.getStringArray(R.array.day_exercises)
        val count = exArray[model.currentDay - 1].split(",")
        return count.size

    }

}
