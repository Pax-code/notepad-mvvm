package com.example.notepadmvvm.presentation.ui


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notepadmvvm.R
import com.example.notepadmvvm.RecyclerAdapter
import com.example.notepadmvvm.data.DBModel
import com.example.notepadmvvm.databinding.FragmentMainScreenBinding
import com.example.notepadmvvm.presentation.ViewModel.ViewModel

class MainScreen : Fragment() {


    private var _binding : FragmentMainScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ViewModel
    private lateinit var recyclerAdapter: RecyclerAdapter




    //fab animation imp
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(requireContext(),
        R.anim.rotate_open_animation
    )}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(requireContext(),
        R.anim.rotate_close_animation
    )}
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(),
        R.anim.from_bottom_animation
    )}
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(),
        R.anim.to_bottom_animation
    )}
    private var clicked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainScreenBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel = ViewModelProvider(requireActivity())[ViewModel::class.java]
        recyclerAdapter = RecyclerAdapter(arrayListOf(),viewModel)
        binding.recyclerView.layoutManager= StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
        binding.recyclerView.adapter= recyclerAdapter


        viewModel.notes.observe(viewLifecycleOwner, Observer {
            recyclerAdapter.updateList(it)
        })

        binding.mainFab.setOnClickListener {
            setVisibility(clicked)
            setAnimation(clicked)
            setClickable(clicked)
            clicked = !clicked
        }

        binding.addNoteFab.setOnClickListener {
            val action = MainScreenDirections.actionMainScreenToAddNoteScreen()
            Navigation.findNavController(it).navigate(action)
            closeMenu()
        }


        binding.editFab.setOnClickListener {
            closeMenu()
        }
        searchBox()

    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked){
            binding.addNoteFab.startAnimation(fromBottom)
            binding.editFab.startAnimation(fromBottom)
            binding.mainFab.startAnimation(rotateOpen)


        }else{
            binding.addNoteFab.startAnimation(toBottom)
            binding.editFab.startAnimation(toBottom)
            binding.mainFab.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {

        if (!clicked){
            binding.addNoteFab.visibility = View.VISIBLE
            binding.editFab.visibility = View.VISIBLE

        }else{
            binding.addNoteFab.visibility = View.GONE
            binding.editFab.visibility = View.GONE

        }

    }


    private fun setClickable(clicked: Boolean){
        if (!clicked){
            binding.addNoteFab.isClickable = true
            binding.editFab.isClickable = true
        }else{
            binding.addNoteFab.isClickable = false
            binding.editFab.isClickable = false
        }
    }


    private fun searchBox(){
        binding.searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val searchText = s.toString().trim()
                filterNotes(searchText)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun filterNotes(searchText: String) {

        val filteredList = arrayListOf<DBModel>()
        for (note in viewModel.notes.value!!) {

            if (note.title.contains(searchText, true) || note.text.contains(searchText, true)) {
                filteredList.add(note)
            }
        }
        recyclerAdapter.updateList(filteredList)
    }

    private fun closeMenu() {
        setAnimation(true)
        setClickable(true)
        setVisibility(true)
        clicked = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}