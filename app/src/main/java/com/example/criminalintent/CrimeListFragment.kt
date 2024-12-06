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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date

private const val TAG = "CrimeListFragment"



class CrimeListFragment : Fragment() {

    private lateinit var crimeRecycleView: RecyclerView
    private var adapter: CrimeAdapter? = null

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total crimes: ${crimeListViewModel.crimes.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list_crime, container, false)

        crimeRecycleView = view.findViewById(R.id.crime_recycle_view) as RecyclerView
        crimeRecycleView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
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

    fun dateFormatter(date: Date): String{
        val calendar: Calendar = Calendar.getInstance()
        val dayOfWeek = when(calendar.get(Calendar.DAY_OF_WEEK) - 1){
            1 -> "Monday"
            2 -> "Thuesday"
            3 -> "Wednesday"
            4 -> "Thurthday"
            5 -> "Friday"
            6 -> "Saturday"
            7 -> "Sunday"
            else -> "Something went wrong"
        }

        val mount = when(calendar.get(Calendar.MONTH) + 1){
            1 -> "Jun"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Apr"
            5 -> "May"
            6 -> "June"
            7 -> "Jul"
            8 -> "Aug"
            9 -> "Sep"
            10 -> "Oct"
            11 -> "Mov"
            12 -> "Dec"
            else -> "error"
        }

        val result: String = dayOfWeek + ", " +
                    mount + " "
                    calendar.get(Calendar.DATE).toString() + ", " +
                    calendar.get(Calendar.YEAR).toString() + ""

        return result
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
    private fun updateUI() {
        val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        crimeRecycleView.adapter = adapter
    }


    companion object{
        fun newInstance(): CrimeListFragment{
            return CrimeListFragment()
        }
    }
}