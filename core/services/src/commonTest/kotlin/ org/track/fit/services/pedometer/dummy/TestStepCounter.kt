package  org.track.fit.services.pedometer.dummy

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.track.fit.services.service.StepCounter

class TestStepCounter(
    val initData:MutableStateFlow<Int> = MutableStateFlow(0)
):StepCounter {

    override val steps: Flow<Int>
        get() = initData
}