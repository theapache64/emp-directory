package com.sifar.whiterabbit.feature.employees

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sifar.whiterabbit.data.remote.employees.Employee
import com.sifar.whiterabbit.data.repo.EmployeeRepo
import com.sifar.whiterabbit.util.calladapter.flow.Resource
import com.sifar.whiterabbit.util.livedata.SingleLiveEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * Created by theapache64 : Feb 17 Wed,2021 @ 17:48
 */
class EmployeesViewModel @ViewModelInject constructor(
    private val employeesRepo: EmployeeRepo
) : ViewModel() {

    /**
     * To search
     */
    private var fullEmployees: List<Employee>? = null
    val searchInput = MutableLiveData("")

    private val _launchEmployeeDetail = SingleLiveEvent<Int>()
    val launchEmployeeDetail: LiveData<Int> = _launchEmployeeDetail

    private val _employeesResp = MutableLiveData<Resource<List<Employee>>>()
    val employeesResp: LiveData<Resource<List<Employee>>> = _employeesResp

    init {
        loadEmployees()
    }


    fun onEmployeeClicked(employee: Employee) {
        _launchEmployeeDetail.value = employee.id
    }

    fun loadEmployees() {

        viewModelScope.launch {

            employeesRepo
                .getEmployees()
                .onStart {
                    // Start
                    _employeesResp.value = Resource.Loading()
                }
                .catch {
                    // error
                    _employeesResp.value = Resource.Error(
                        it.message ?: "Something went wrong"
                    )
                }
                .collect {
                    // Success
                    fullEmployees = it
                    _employeesResp.value = Resource.Success(null, fullEmployees!!)
                }
        }

    }

    fun onSearchInputChanged(_keyword: String) {
        if (fullEmployees != null) {
            // only if data loaded
            val keyword = _keyword.trim()
            val filteredResult = fullEmployees!!.filter {
                it.name.contains(keyword, true) || it.email.contains(keyword, true)
            }
            _employeesResp.value = Resource.Success(null, filteredResult)
        }
    }


}