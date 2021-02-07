package com.alivecor.assignment.viewmodels

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alivecor.assignment.R
import com.alivecor.assignment.models.UserDetails
import java.text.SimpleDateFormat
import java.util.*


class UserProfileViewModel : ViewModel() {

    lateinit var userMutableLiveData: MutableLiveData<UserDetails>

    val userName = MutableLiveData<String>()
    val userLastName = MutableLiveData<String>()
    val userDob = MutableLiveData<String>()
    val userAge = MutableLiveData<String>()

    /*get user data*/
    fun getUser(): MutableLiveData<UserDetails> {
        userMutableLiveData = MutableLiveData<UserDetails>()
        return userMutableLiveData
    }


    fun getUserDataForDetails(): MutableLiveData<UserDetails> {
        return userMutableLiveData
    }

    /* showing date picker */
    private fun showDatePicker(context: Context) {
        val c: Calendar = Calendar.getInstance()

        // Launch Date Picker Dialog with current date

        val datePickerDialog = DatePickerDialog(
            context, { view, year, monthOfYear, dayOfMonth ->
                val cal: Calendar = Calendar.getInstance()
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                userDob.setValue(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                calculateAge(cal)

            },
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = c.getTimeInMillis()
        datePickerDialog.show()
    }

    /*handle click event on show profile button and date text */
    fun onClick(view: View?) {
        when (view?.id) {
            R.id.edt_dob -> showDatePicker(view.context)
            R.id.btn_show_user_profile_details -> {
                val userDetails = UserDetails(
                    userName.getValue(),
                    userLastName.getValue(),
                    userDob.getValue()
                )
                userMutableLiveData.setValue(userDetails)
            }
        }


    }

    private fun calculateAge(dobCalendar: Calendar): MutableLiveData<String> {

        val showDate: String
        try {

            val cal = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            val currentDate = dateFormat.format(cal.getTime())

            val dateFormatDob = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            val dobDate = dateFormatDob.format(dobCalendar.getTime())

            val dates = SimpleDateFormat("MM/dd/yyyy", Locale.US)

            val dob = dates.parse(dobDate)
            val today = dates.parse(currentDate)
            val difference = Math.abs(dob.time - today.time)
            val differenceDates = difference / (24 * 60 * 60 * 1000)
            val year = differenceDates / 365
            var month = 0
            val days: Int
            val remaingDays = differenceDates - year * 365
            if (remaingDays > 30) {
                month = (remaingDays / 30).toInt()
                days = (differenceDates - year * 365 - month * 30).toInt()
                showDate = "Age : $year years , $month months , $days days"
                userAge.setValue(showDate)
            } else if (remaingDays.toInt() == 30) {
                days = remaingDays.toInt()
                showDate = "Age : $year years , $month months , $days days"
                userAge.setValue(showDate)
            } else {
                days = remaingDays.toInt()
                showDate = "Age : $year years , $month months , $days days"
                userAge.setValue(showDate)
            }

        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return userAge
    }
}