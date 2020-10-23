package com.kt.basedata.remote.auth

import okhttp3.internal.notify
import okhttp3.internal.wait
import java.util.concurrent.atomic.AtomicReference

abstract class AsyncRunnable<T> {
    protected abstract fun run(notifier: AtomicReference<T>?)
    protected fun finish(notifier: AtomicReference<T>, result: T) {
        synchronized(notifier) {
            notifier.set(result)
            notifier.notify()
        }
    }

    companion object {
        fun <T> wait(runnable: AsyncRunnable<T?>): T? {
            val notifier = AtomicReference<T?>()
            runnable.run(notifier)
            synchronized(notifier) {
                while (notifier.get() == null) {
                    try {
                        notifier.wait()
                    } catch (ignore: InterruptedException) {
                    }
                }
            }
            return notifier.get()
        }
    }
}