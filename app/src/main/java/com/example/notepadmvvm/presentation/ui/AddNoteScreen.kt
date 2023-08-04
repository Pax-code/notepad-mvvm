package com.example.notepadmvvm.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.notepadmvvm.data.DBModel
import com.example.notepadmvvm.databinding.FragmentAddNoteScreenBinding
import com.example.notepadmvvm.presentation.ViewModel.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddNoteScreen : Fragment() {

    private var _binding : FragmentAddNoteScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ViewModel::class.java]

        binding.saveFab.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val userInput = binding.addNoteText.text.toString().trim()


        if (userInput.isBlank()) {
            return
        }

        val firstWord = userInput.split(" ").first()

        val dbModel = DBModel(title = firstWord, text = userInput, SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
            Date()
        ))
        viewModel.insert(dbModel)


        val action = AddNoteScreenDirections.actionAddNoteScreenToMainScreen()
        Navigation.findNavController(binding.saveFab).navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
