package com.nlv2022.trenaghorik.Fragments

import android.os.Binder
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nlv2022.trenaghorik.R
import com.nlv2022.trenaghorik.adapters.DayModel
import com.nlv2022.trenaghorik.adapters.DaysAdapter
import com.nlv2022.trenaghorik.adapters.ExerciseAdapter
import com.nlv2022.trenaghorik.databinding.ExercisesListFragmentBinding
import com.nlv2022.trenaghorik.databinding.FragmentDaysBinding
import com.nlv2022.trenaghorik.databinding.WaitingFragmentBinding
import com.nlv2022.trenaghorik.utils.FragmentManager
import com.nlv2022.trenaghorik.utils.MainViewModel
import com.nlv2022.trenaghorik.utils.TimeUtils

const val COUNT_DOWN_TIME = 4000L

class WaitingFragment : Fragment() {
    private lateinit var binding: WaitingFragmentBinding
    private lateinit var timer: CountDownTimer
    private var ab: ActionBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WaitingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    } // создание фрагмента. Переопределение интерфейса

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ab = (activity as AppCompatActivity).supportActionBar
        ab?.title=getString(R.string.waiting)

        binding.pBar.max = COUNT_DOWN_TIME.toInt()
        startTimer()


    }


    private fun startTimer() = with(binding) {
        timer = object : CountDownTimer(COUNT_DOWN_TIME, 10) {
            override fun onTick(restTime: Long) {
                tvTimer.text = TimeUtils.getTime(restTime)
                pBar.progress = restTime.toInt()
            }

            override fun onFinish() {
                Toast.makeText(activity, "Поехали", Toast.LENGTH_LONG).show()
                FragmentManager.setFragment(
                    ExercisesFragment.newInstance(),
                    activity as AppCompatActivity
                )
            }

        }.start()

    }

    override fun onDetach() {
        super.onDetach()
        timer.cancel()
    }

    companion object {

        @JvmStatic
        fun newInstance() = WaitingFragment()
    }
}
