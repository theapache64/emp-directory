package com.sifar.whiterabbit.data.remote

import com.sifar.whiterabbit.data.remote.employees.Employee
import com.sifar.whiterabbit.util.calladapter.flow.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

/**
 * Created by theapache64 : Feb 17 Wed,2021 @ 17:43
 */
interface ApiInterface {

    @GET("v2/5d565297300000680030a986")
    fun getEmployees(): Flow<Resource<List<Employee>>>

}