package com.sifar.whiterabbit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sifar.whiterabbit.data.local.dao.EmployeeDao
import com.sifar.whiterabbit.data.remote.employees.Employee

/**
 * Created by theapache64 : Feb 17 Wed,2021 @ 17:50
 */
@Database(entities = [Employee::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}