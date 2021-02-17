package com.sifar.whiterabbit.data.repo

import com.sifar.whiterabbit.data.local.dao.EmployeeDao
import com.sifar.whiterabbit.data.remote.ApiInterface
import com.sifar.whiterabbit.data.remote.employees.Employee
import com.sifar.whiterabbit.util.NetworkBoundResource
import com.sifar.whiterabbit.util.calladapter.flow.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by theapache64 : Feb 17 Wed,2021 @ 17:50
 */
class EmployeeRepo @Inject constructor(
    private val apiInterface: ApiInterface,
    private val employeeDao: EmployeeDao
) {
    /**
     * To get employees
     */
    fun getEmployees(): Flow<List<Employee>> {
        return object : NetworkBoundResource<List<Employee>, List<Employee>>() {

            override fun fetchFromLocal(): Flow<List<Employee>> {
                return employeeDao.getEmployees()
            }

            override fun fetchFromRemote(): Flow<Resource<List<Employee>>> {
                return apiInterface.getEmployees()
            }

            override fun saveRemoteData(data: List<Employee>) {
                employeeDao.nukeTableAndAdd(data)
            }

            override suspend fun shouldFetchFromRemote(data: List<Employee>): Boolean {
                // fetch from remote if room is empty
                return employeeDao.getEmployeesCount() == 0
            }

        }.asFlow().flowOn(Dispatchers.IO)
    }

    suspend fun getEmployee(empId: Int) = employeeDao.getEmployee(empId)
}