package com.example.criminalintent

import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import java.util.UUID
import java.util.logging.Handler

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
        val crimeId: UUID = arguments?.getSerializable(ARG_CRIME_ID) as UUID
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
            viewLifecycleOwner,
            Observer { crime ->
                Log.d(TAG, "Observer triggered with crime: $crime")
                android.os.Handler(Looper.getMainLooper()).postDelayed({
                    Log.d(TAG, "Delayed check: crime = $crime")
                }, 2000) // Через 2 секунды ещё раз проверим
//                Log.d(TAG, "Observer triggered with crime: $crime")
//                crime?.let {
//                    this.crime = crime
//                    updateUI()
//                    Log.d(TAG, "updateUI called")
//                } ?: Log.d(TAG, "Crime is null")
            }
        )
    }

    private fun updateUI(){
        tv_crime_title.setText(crime.title)
//        Log.d(TAG, "Заполняем ${crime.title}")
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
                TODO("Not yet implemented")
            }

            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                crime.title = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                TODO("Not yet implemented")
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
        fun newInstance(crimeId: UUID): CrimeFragment{
            val args = Bundle().apply{
                putSerializable(ARG_CRIME_ID, crimeId)
            }
            return CrimeFragment().apply {
                arguments = args
            }
        }
    }
}

