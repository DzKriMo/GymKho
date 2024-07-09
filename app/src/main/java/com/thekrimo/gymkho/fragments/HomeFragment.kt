package com.thekrimo.gymkho.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thekrimo.gymkho.databinding.FragmentHomeBinding
import com.thekrimo.gymkho.details.NutritionDetails
import java.util.Calendar

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("name", "")
        binding.hiText.text = "Hi $username"

        val calendar = Calendar.getInstance()
        val monthNumb = calendar.get(Calendar.MONTH)
        val today = calendar.get(Calendar.DAY_OF_MONTH)
        val months = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        val month = months[monthNumb]
        binding.monthName.text = month
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        binding.day1.text = ((today - 2).takeIf { it > 0 } ?: daysInMonth-1).toString()
        binding.day2.text = ((today - 1).takeIf { it > 0 } ?: daysInMonth).toString()
        binding.day3.text = today.toString()
        binding.day4.text = ((today + 1).takeIf { it <= daysInMonth } ?: (1 - (daysInMonth - today))).toString()
        binding.day5.text = ((today + 2).takeIf { it <= daysInMonth } ?: (2 - (daysInMonth - today))).toString()

        binding.progress.progress = 69
        binding.progressText.text = "69%"
        binding.trainingProgress.progress = 83

        binding.details.setOnClickListener {
            requireActivity().startActivity(Intent(requireActivity(),NutritionDetails::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
