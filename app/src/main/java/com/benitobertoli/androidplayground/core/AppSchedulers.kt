package com.benitobertoli.androidplayground.core

import io.reactivex.Scheduler

interface AppSchedulers {
    val backgroundScheduler: Scheduler
    val foregroundScheduler: Scheduler
}