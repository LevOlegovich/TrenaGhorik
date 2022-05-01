package com.nlv2022.trenaghorik.Fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nlv2022.trenaghorik.R
import com.nlv2022.trenaghorik.adapters.DayModel
import com.nlv2022.trenaghorik.adapters.DaysAdapter
import com.nlv2022.trenaghorik.adapters.ExerciseModel
import com.nlv2022.trenaghorik.databinding.FragmentDaysBinding
import com.nlv2022.trenaghorik.utils.DialogManager
import com.nlv2022.trenaghorik.utils.FragmentManager
import com.nlv2022.trenaghorik.utils.MainViewModel

class DaysFragment : Fragment(), DaysAdapter.Listener {
    private lateinit var adapter: DaysAdapter
    private lateinit var binding: FragmentDaysBinding
    private val model: MainViewModel by activityViewModels() //13
    private var ab: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) { //28
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    ///////
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    } // создание фрагмента. Переопределение интерфейса


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentDay = 0
        initRcView()
    }
///////

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) { //28
        return inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.reset) {
            DialogManager.showDialog(activity as AppCompatActivity,
                R.string.reset_days_message,
                object : DialogManager.Listener {
                    override fun onClick() {
                        model.pref?.edit()?.clear()?.apply()
                        adapter.submitList(fillDaysArray())
                    }

                })

        }
        return super.onOptionsItemSelected(item)
    }


    // моя
    private fun initRcView() = with(binding) {

        ab = (activity as AppCompatActivity).supportActionBar
        ab?.title = getString(R.string.days)

        adapter = DaysAdapter(this@DaysFragment)
        rcViewDays.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        rcViewDays.adapter = adapter
        adapter.submitList(fillDaysArray())
    }

    // моя
    private fun fillDaysArray(): ArrayList<DayModel> { // добавление и приведение массива в Recycler View из ресурсов
        //Внешний массив из 20 элементов(дней)
        val tArray = ArrayList<DayModel>()
        var daysDoneCounter = 0
        resources.getStringArray(R.array.day_exercises).forEach {
            model.currentDay++
            val exCounter = it.split(",").size
            tArray.add(DayModel(it, 0, model.getExerciseCount() == exCounter))
        }
        binding.pB.max = tArray.size
        tArray.forEach {
            if (it.isDone) daysDoneCounter++

        }
        updateRestDaysUi(tArray.size - daysDoneCounter, tArray.size)
        return tArray
    }


    private fun updateRestDaysUi(restDays: Int, days: Int) = with(binding) {
        val rDays = getString(R.string.rest) + " $restDays " + getString(R.string.rest_days)
        tvRestDays.text = rDays
        pB.progress = days - restDays

    }


    private fun fillExerciseList(day: DayModel) {   // 12
        val tempList = ArrayList<ExerciseModel>()
        day.exercises.split(",").forEach() {
            val exerciseList =
                resources.getStringArray(R.array.exercise) // полный массив из всех типов упражнений
            val exercise =
                exerciseList[it.toInt()]  // выбор упражения по текущему элементу из цикла
            // toInt потому что в цикле элементы стринговые цифры

            val exerciseArray = exercise.split("|")  // создаем новый массив, разделяем(сортируем)
            tempList.add(
                ExerciseModel(
                    exerciseArray[0],
                    exerciseArray[1],
                    false,
                    exerciseArray[2]
                )
            )
        }
        model.mutableListExercise.value = tempList


    }

    // содержится по умолчанию
    companion object {

        @JvmStatic
        fun newInstance() = DaysFragment()
    }


    // активная
    override fun onClick(day: DayModel) {
        if (!day.isDone) {
            fillExerciseList(day)
            model.currentDay = day.dayNumber

            FragmentManager.setFragment(
                ExercisesListFragment.newInstance(),
                activity as AppCompatActivity
            )
        } else {
            DialogManager.showDialog(activity as AppCompatActivity,
                R.string.reset_day_message,
                object : DialogManager.Listener {
                    override fun onClick() {
                        model.savePref(day.dayNumber.toString(), 0)
                        adapter.submitList(fillDaysArray())
                        fillExerciseList(day)
                        model.currentDay = day.dayNumber

                        FragmentManager.setFragment(
                            ExercisesListFragment.newInstance(),
                            activity as AppCompatActivity)
                    }


                })
        }


    }
}
