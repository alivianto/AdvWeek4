package com.ubaya.advweek4.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ubaya.advweek4.R
import com.ubaya.advweek4.util.loadImage
import com.ubaya.advweek4.viewmodel.DetailViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_student_detail.*
import kotlinx.android.synthetic.main.student_list_item.view.*
import java.util.concurrent.TimeUnit

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
            Log.d("hasil",student.toString())
            student?.let {
                imageStudentDetailPhoto.loadImage(it.photoURL, progressLoadingStudentDetailPhoto)
                editID.setText(it.id)
                editName.setText(it.name)
                editDOB.setText(it.dob)
                editPhone.setText(it.phone)

                buttonNotif.setOnClickListener {
                    Observable.timer(5, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            Log.d("mynotif", "Notification delayed after 5 seconds")
                            MainActivity.showNotification(student.name.toString(), "Notification created",
                            R.drawable.ic_baseline_person_24)
                        }
                }
            }

        }
    }
}