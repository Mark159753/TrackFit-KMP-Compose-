package  org.track.fit.services.pedometer.dummy

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.LocalDateTime
import org.track.fit.local.preferences.AppPreferences

class TestPreferences(
    initValue: LocalDateTime? = null
): AppPreferences {

    private val _startTime = MutableStateFlow<LocalDateTime?>(initValue)
    override val questStartTime: Flow<LocalDateTime?>
        get() = _startTime

    override suspend fun setStartTime(time: LocalDateTime) {
        _startTime.value = time
    }
}