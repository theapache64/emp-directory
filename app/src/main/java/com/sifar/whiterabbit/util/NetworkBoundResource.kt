package com.sifar.whiterabbit.util

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.sifar.whiterabbit.util.calladapter.flow.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.io.IOException

/**
 * A super cool utility class to provide controlled data cache-ing
 */
abstract class NetworkBoundResource<DB, REMOTE> {

    @MainThread
    abstract fun fetchFromLocal(): Flow<DB>

    @MainThread
    abstract fun fetchFromRemote(): Flow<Resource<REMOTE>>

    @WorkerThread
    abstract fun saveRemoteData(data: REMOTE)

    @MainThread
    abstract suspend fun shouldFetchFromRemote(data: DB): Boolean

    @ExperimentalCoroutinesApi
    fun asFlow() = flow<DB> {


        val localData = fetchFromLocal().first()

        // checking if local data is staled
        if (shouldFetchFromRemote(localData)) {

            // need remote data
            fetchFromRemote()
                .collect { response ->
                    if (response is Resource.Success) {
                        val data = response.data!!
                        saveRemoteData(data)

                        // start watching it
                        emitLocalDbData()
                    } else if (response is Resource.Error) {
                        throw IOException(response.errorData)
                    }
                }

        } else {
            // valid cache, no need to fetch from remote.
            emitLocalDbData()
        }
    }

    private suspend fun FlowCollector<DB>.emitLocalDbData() {
        emitAll(fetchFromLocal())
    }
}