package com.example.criminalintent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import java.util.UUID

private const val TAG = "CrimeFragment"
private const val ARG_CRIME_ID = "crime_id"

class CrimeFragment : Fragment() {

    private lateinit var crime: Crime
    private lateinit var tv_crime_title: EditText
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox: CheckBox
    private val crimeDetailViewModel: CrimeDetailViewModel by lazy {
        ViewModelProviders.of(this)[CrimeDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crime = Crime()
        //Стандартный доступ к переданным аргументам из CrListFragment -> MainActivity -> this
//        val crimeId: UUID = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        val crimeId: String = arguments?.getSerializable(ARG_CRIME_ID) as String
        Log.d(TAG, "args of bundle crimeID: $crimeId")
        crimeDetailViewModel.loadCrime(crimeId)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_crime, container, false)
        tv_crime_title = view.findViewById<EditText>(R.id.crimeTitle)
        solvedCheckBox = view.findViewById<CheckBox>(R.id.solved_checkbox)
        dateButton = view.findViewById<Button>(R.id.date_button)

        dateButton.apply{
            text = crime.date.toString()
            isEnabled = false
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeDetailViewModel.crimeLD.observe(
            viewLifecycleOwner
        ) { fetchedCrime ->
            fetchedCrime?.let {
                // Обновляем локальный объект crime полученными данными
                crime = fetchedCrime
                updateUI()
                Log.d(TAG, "Crime loaded and UI updated: $fetchedCrime")
            } ?: Log.d(TAG, "Fetched crime is null")
        }
    }

    private fun updateUI(){
        tv_crime_title.setText(crime.title)
        dateButton.text = crime.date.toString()
        solvedCheckBox.apply{
            isChecked = crime.isSolved == 1
            jumpDrawablesToCurrentState()
        }
    }

    override fun onStart(){
        super.onStart()

        val titleWatcher = object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                crime.title = p0.toString()
            }

            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                crime.title = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                crime.title = sequence.toString()
            }
        }

        solvedCheckBox.apply{
            setOnCheckedChangeListener { _, isChecked ->
                crime.isSolved = if(isChecked) 1 else 0
            }
        }
        tv_crime_title.addTextChangedListener(titleWatcher)
    }

    // Хранение аргументов фрагмента (как в activity)
    companion object{
        fun newInstance(crimeId: String): CrimeFragment{
            val args = Bundle().apply{
                putSerializable(ARG_CRIME_ID, crimeId)
            }
            return CrimeFragment().apply {
                arguments = args
            }
        }
    }
}

