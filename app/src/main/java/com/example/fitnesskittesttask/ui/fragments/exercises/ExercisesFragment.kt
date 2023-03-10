package com.example.fitnesskittesttask.ui.fragments.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesskittesttask.adapter.TrainingRecyclerViewAdapter
import com.example.fitnesskittesttask.databinding.FragmentExercisesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExercisesFragment : Fragment() {

    private var _binding: FragmentExercisesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExercisesViewModel by viewModels()

    @Inject
    lateinit var adapter: TrainingRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExercisesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = adapter

        viewModel.exercises.observe(viewLifecycleOwner) { newList ->
            adapter.updateSchedules(newList = newList)
        }

        viewModel.progressBar.observe(viewLifecycleOwner) {
            if (it) binding.progressbar.visibility = View.VISIBLE
            else binding.progressbar.visibility = View.GONE
        }

        viewModel.toast.observe(viewLifecycleOwner) { message ->
            message?.let {
                viewModel.refreshToast()
                Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
            }
        }

        val swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshSchedulesLoad()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.clearCompositeDisposable()
    }
}