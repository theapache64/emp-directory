package com.sifar.whiterabbit.feature.employeedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sifar.whiterabbit.R
import com.sifar.whiterabbit.data.remote.employees.Employee
import com.sifar.whiterabbit.data.repo.EmployeeRepo
import com.sifar.whiterabbit.util.livedata.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by theapache64 : Feb 17 Wed,2021 @ 19:26
 */
@HiltViewModel
class EmployeeDetailViewModel @Inject constructor(
    private val employeeRepo: EmployeeRepo
) : ViewModel() {
    private val _employee = MutableLiveData<Employee>()
    val employee: LiveData<Employee> = _employee

    private val _shouldNavBack = SingleLiveEvent<Boolean>()
    val shouldNavBack: LiveData<Boolean> = _shouldNavBack

    private val _employeeDetails = MutableLiveData<List<EmployeeDetail>>()
    val employeeDetails: LiveData<List<EmployeeDetail>> = _employeeDetails

    fun init(
        employeeId: Int
    ) {
        loadEmployee(employeeId)
    }

    private fun loadEmployee(employeeId: Int) {
        viewModelScope.launch {
            val emp = employeeRepo.getEmployee(employeeId)
            _employee.value = emp

            // Create detail nodes
            _employeeDetails.value = listOf(
                EmployeeDetail(R.string.emp_detail_username, emp.username),
                EmployeeDetail(R.string.emp_detail_email, emp.email),
                EmployeeDetail(R.string.emp_detail_address, getReadable(emp.address)),
                EmployeeDetail(R.string.emp_detail_website, emp.website ?: "-"),
                EmployeeDetail(R.string.emp_detail_company, emp.company?.companyName ?: "-"),
            )
        }
    }


    private fun getReadable(address: Employee.Address): String {
        return " ${address.street}, ${address.city}, ${address.zipcode}"
    }

    fun onBackClicked() {
        _shouldNavBack.value = true
    }
}