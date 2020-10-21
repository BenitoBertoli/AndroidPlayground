package com.benitobertoli.androidplayground.core

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AppSchedulersImpl @Inject constructor(): AppSchedulers {
    override val backgroundScheduler: Scheduler = Schedulers.io()
    override val foregroundScheduler: Scheduler = AndroidSchedulers.mainThread()
}