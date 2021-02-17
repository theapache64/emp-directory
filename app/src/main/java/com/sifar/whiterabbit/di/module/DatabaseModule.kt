package com.sifar.whiterabbit.di.module

import android.content.Context
import androidx.room.Room
import com.sifar.whiterabbit.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * Created by theapache64 : Feb 17 Wed,2021 @ 18:02
 */
@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    private const val DB_NAME = "com.sifar.whiterabbit_db"

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            .build()
    }

    @Provides
    fun provideEmployeeDao(appDatabase: AppDatabase) = appDatabase.employeeDao()
}