package com.kt.baseusercase.usecase

import androidx.annotation.IntRange
import com.kt.basedata.local.CoreSharePreference
import javax.inject.Inject

class ChangeEnvironment @Inject constructor(val coreSharePreference: CoreSharePreference) {
    operator fun invoke(@IntRange(from = 0L, to = 2L) env: Int) =
        coreSharePreference.saveEnvironment(env)
}