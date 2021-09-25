package com.oshaev.artclub20.presentation.studioshedule.addbooking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.oshaev.artclub20.R
import com.oshaev.artclub20.presentation.viewmodels.AddBookingViewModel
import com.oshaev.artclub20.repository.BookingModel
import java.util.*

class AddBookingActivity: AppCompatActivity() {

    private lateinit var model: AddBookingViewModel
    private lateinit var dateText: TextView
    private lateinit var startTimeText: TextView
    private lateinit var endTimeText: TextView
    private lateinit var cabinetSpinner: Spinner
    private lateinit var instrumentOne: CheckBox
    private lateinit var instrumentTwo: CheckBox
    private lateinit var instrumentThree: CheckBox
    private lateinit var button: Button
    private var startCalendar: GregorianCalendar = GregorianCalendar()
    private var endCalendar: GregorianCalendar = GregorianCalendar()
    private lateinit var booking: BookingModel
    lateinit var spinnerAdapter: ArrayAdapter<*>
    var cabinet = "115"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_add_booking)

        cabinetSpinner = findViewById(R.id.add_booking_cabinet_spinner)
        dateText = findViewById(R.id.add_booking_date_content)
        startTimeText = findViewById(R.id.add_booking_start_time_content)
        endTimeText = findViewById(R.id.add_booking_end_time_content)
        instrumentOne = findViewById(R.id.add_booking_instrument_one_check)
        instrumentTwo = findViewById(R.id.add_booking_instrument_two_check)
        instrumentThree = findViewById(R.id.add_booking_instrument_three_check)
        button = findViewById(R.id.add_booking_button)
        var intent = intent

        model = ViewModelProvider(this).get(AddBookingViewModel::class.java)
        var datePickerDialog = DatePickerDialog(
            this,
        )
        var startListener: TimePickerDialog.OnTimeSetListener =
            TimePickerDialog.OnTimeSetListener { picker, hours, minutes ->
                startCalendar.set(Calendar.HOUR_OF_DAY, hours)
                startCalendar.set(Calendar.MINUTE, minutes)
                startCalendar.set(Calendar.ZONE_OFFSET, 3)
                startTimeText.text = hours.toString() + " ч. " + minutes.toString() + " мин."
            }
        var endListener: TimePickerDialog.OnTimeSetListener =
            TimePickerDialog.OnTimeSetListener { picker, hours, minutes ->
                endCalendar.set(Calendar.HOUR_OF_DAY, hours)
                endCalendar.set(Calendar.MINUTE, minutes)
                endCalendar.set(Calendar.ZONE_OFFSET, 3)
                endTimeText.text = hours.toString() + " ч. " + minutes.toString() + " мин."
            }
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, day ->
            startCalendar.set(year, month, day)
            endCalendar.set(year, month, day)
            Log.d("AddBooking", day.toString() + " " + month.getMonthName())
            dateText.text = day.toString() + " " + month.getMonthName()
        }, 2021, 1, 1)
        val startTimePickerDialog = TimePickerDialog(
            this, startListener,
            0, 0, true
        )
        val endTimePickerDialog = TimePickerDialog(
            this, endListener,
            0, 0, true
        )

        startTimeText.setOnClickListener {
            startTimePickerDialog.show()
        }


        endTimeText.setOnClickListener {
            endTimePickerDialog.show()
        }

        button.setOnClickListener {
            booking = BookingModel(
                id = UUID.randomUUID().toString(),
                cabinet = cabinet,
                timestampStart = startCalendar.time.time-10800000,
                timestampFinish = endCalendar.time.time-10800000,
                uid = FirebaseAuth.getInstance().uid.toString(),
                members = emptyMap(),
                instruments = emptyMap(),
            )
            Log.d("AddBooking", booking.toString())
            model.sendBookingToDB(booking)
            finish()
        }

        dateText.setOnClickListener {
            dpd.show()
        }

        setSpinner()
    }

    private fun setSpinner() {
        spinnerAdapter =
            ArrayAdapter
                .createFromResource(this, R.array.cabinets, R.layout.support_simple_spinner_dropdown_item)
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        cabinetSpinner.adapter = spinnerAdapter
        cabinetSpinner.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                cabinet = resources.getStringArray(R.array.cabinets)[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun Int.getMonthName(): String {
        when (this) {
            0 -> return resources.getString(com.oshaev.artclub20.R.string.month_january)
            1 -> return resources.getString(R.string.month_february)
            2 -> return resources.getString(R.string.month_march)
            3 -> return resources.getString(R.string.month_april)
            4 -> return resources.getString(R.string.month_may)
            5 -> return resources.getString(R.string.month_june)
            6 -> return resources.getString(R.string.month_july)
            7 -> return resources.getString(R.string.month_august)
            8 -> return resources.getString(R.string.month_september)
            9 -> return resources.getString(R.string.month_october)
            10 -> return resources.getString(R.string.month_november)
            11 -> return resources.getString(R.string.month_december)
            else -> return ""
        }
    }
}