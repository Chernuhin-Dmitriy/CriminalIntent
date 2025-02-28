package com.example.criminalintent

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {

    private lateinit var crimeRecycleView: RecyclerView
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Log.d(TAG, "Total crimes: ${crimeListViewModel.crimes.size}")
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list_crime, container, false)

        crimeRecycleView = view.findViewById(R.id.crime_recycle_view) as RecyclerView
        crimeRecycleView.layoutManager = LinearLayoutManager(context)

        crimeRecycleView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLD.observe(
            viewLifecycleOwner,
            Observer { crimes ->
                crimes?.let{
                    Log.i(TAG, "Got crimes ${crimes.size}")
                    updateUI(crimes)
                }
            }
        )
    }


    //Конструктор viewHolder`a
    private inner class CrimeHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var crime: Crime
        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private val solvedImageView: ImageView = itemView.findViewById(R.id.solved_crime)


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Toast.makeText(itemView.context, crime.title, Toast.LENGTH_SHORT).show()
        }

        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = liteDateFormatter(this.crime.date)        //dateFormatter(this.crime.date)        //toString().substringBefore('G') //Monday, Jul 22, 2019

            solvedImageView.visibility = if (crime.isSolved)
                    View.VISIBLE
                else
                    View.GONE
        }
    }

    fun liteDateFormatter(today: Date): String{
        val dateFormat = SimpleDateFormat("EEEE, MMM dd, yyyy")

        val formattedDate = dateFormat.format(today)
        return formattedDate
    }


    private inner class CrimeAdapter(var crimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {

            val layoutType = when(viewType){
                1 -> R.layout.list_item_heavy_crime
                else -> R.layout.list_item_crime
            }

            val view = layoutInflater.inflate(layoutType, parent, false)

            return CrimeHolder(view)
        }

        override fun getItemCount() = crimes.size

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }

        //Логика получения ViewType для представления
        override fun getItemViewType(position: Int): Int {
            return crimes[position].requiresPolice
        }
    }


    // Данные -> в экземпляр адаптера -> в recycleview адаптер
    private fun updateUI(crimes: List<Crime>) {
        adapter = CrimeAdapter(crimes)
        crimeRecycleView.adapter = adapter
    }


    companion object{
        fun newInstance(): CrimeListFragment{
            return CrimeListFragment()
        }
    }
}