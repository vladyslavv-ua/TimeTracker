package io.vladyslavv_ua.time_tracker.repo

import io.vladyslavv_ua.time_tracker.datasource.room.AppDB
import io.vladyslavv_ua.time_tracker.entity.TimeLap

class TimeLapRepo(appDB: AppDB) {
    private val timeLapDao = appDB.timeLapDao()
    suspend fun addTimeLap(timeLap: TimeLap) = timeLapDao.addTimeLap(timeLap)
    suspend fun updateTimeLap(timeLap: TimeLap) = timeLapDao.updateTimeLap(timeLap)
    suspend fun getTimeLapById(id:Long) = timeLapDao.getTimeLapById(id)
    suspend fun updateLabel(id: Long, label: String) = timeLapDao.updateTimeLapLabel(id, label)
    suspend fun deleteTimeLap(id: Long) = timeLapDao.deleteTimeLap(id)
}