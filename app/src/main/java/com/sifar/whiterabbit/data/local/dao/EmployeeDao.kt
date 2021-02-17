package com.sifar.whiterabbit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.sifar.whiterabbit.data.remote.employees.Employee
import kotlinx.coroutines.flow.Flow

/**
 * Created by theapache64 : Feb 17 Wed,2021 @ 17:52
 */
@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employees")
    fun getEmployees(): Flow<List<Employee>>

    @Query("SELECT COUNT(*) FROM employees")
    suspend fun getEmployeesCount(): Int

    @Insert
    fun addAll(employees: List<Employee>)

    @Query("DELETE FROM employees")
    fun deleteAll()

    @Transaction
    fun nukeTableAndAdd(data: List<Employee>) {
        deleteAll()
        addAll(data)
    }

    @Query("SELECT * FROM employees WHERE id = :empId")
    suspend fun getEmployee(empId: Int): Employee
}