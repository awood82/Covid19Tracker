package com.androidandrew.covid19tracker

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay


fun <T> T.toDeferred(time: Long = 0L) = GlobalScope.async {
    if (time > 0) delay(time)
    this@toDeferred
}
