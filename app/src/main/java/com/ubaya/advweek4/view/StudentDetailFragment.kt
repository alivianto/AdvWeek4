package com.ubaya.advweek4.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ubaya.advweek4.R
import com.ubaya.advweek4.util.loadImage
import com.ubaya.advweek4.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_student_detail.*
import kotlinx.android.synthetic.main.student_list_item.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [StudentDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentDetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var id = ""
        arguments?.let{
            id = StudentDetailFragmentArgs.fromBundle(requireArguments()).id
        }
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.fetch(id)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.studentLiveData.observe(viewLifecycleOwner) {
            val student = viewModel.studentLiveData.value
            student?.let {
                imageStudentDetailPhoto.loadImage(it.photoURL, progressLoadingStudentDetailPhoto)
                editID.setText(it.id)
                editName.setText(it.name)
                editDOB.setText(it.dob)
                editPhone.setText(it.phone)
                //imageStudentPhoto.loadImage(student.photoURL, progressLoadingStudentPhoto)
            }

        }
    }
}