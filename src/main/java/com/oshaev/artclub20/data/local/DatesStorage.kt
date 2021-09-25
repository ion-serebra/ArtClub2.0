package com.oshaev.artclub20.data.local

import com.oshaev.artclub20.presentation.studioshedule.ScreenScheduleDateView
import java.util.*

class DatesStorage {

    var calendar = GregorianCalendar()
    var datesList = mutableListOf<Calendar>()

    fun getTwoWeeks(): List<Calendar> {
        for (i in 1..14){
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            datesList.add(calendar)
        }
        return datesList
    }
}