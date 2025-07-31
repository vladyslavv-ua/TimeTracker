package io.vladyslavv_ua.time_tracker.di

import io.vladyslavv_ua.time_tracker.feature.fullStatistics.FullStatisticsScreenVM
import io.vladyslavv_ua.time_tracker.feature.project.ProjectViewModel
import io.vladyslavv_ua.time_tracker.feature.projectStatistics.ProjectStatisticsViewModel
import io.vladyslavv_ua.time_tracker.feature.projects.ProjectsViewModel
import io.vladyslavv_ua.time_tracker.feature.timeLapInfo.TimeLapInfoViewModel
import org.koin.dsl.module

val sharedVM = module {
    factory { ProjectsViewModel(get()) }
    factory { (id: Long) -> ProjectViewModel(id, get(), get()) }
    factory { (id: Long) -> TimeLapInfoViewModel(id, get()) }
    factory { (id: Long) -> ProjectStatisticsViewModel(id, get()) }
}