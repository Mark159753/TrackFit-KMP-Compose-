package org.track.fit.data.repository.statistics

import co.touchlab.kermit.Logger
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import org.track.fit.common.date.now
import org.track.fit.common.date.toDayMilliseconds
import org.track.fit.common.date.toLocalDateTime
import org.track.fit.data.models.DailyAchievements
import org.track.fit.data.models.DateAchievements

class StatisticsRepositoryImpl(
    private val database: DatabaseReference
) : StatisticsRepository {

    private val auth = Firebase.auth
    private val path: DatabaseReference
        get() = database.child(auth.currentUser?.uid + DAILY_ACHIEVEMENT)

    override val todayAchievements: Flow<DailyAchievements?>
        get() {
            val todayMillis = LocalDateTime.now().toDayMilliseconds()
            return path.child(todayMillis.toString())
                .valueEvents
                .catch { e ->
                    Logger.e(tag = "todayAchievements", throwable = e, messageString = "ERROR")
                }
                .map { snapshot ->
                if (snapshot.exists){
                    val map = snapshot.value as Map<*, *>
                    DailyAchievements.toDailyAchievements(map)
                }else{
                    null
                }
            }
        }

    override val total: Flow<DailyAchievements?> = path.valueEvents
        .catch { e ->
            Logger.e(tag = "total", throwable = e, messageString = "ERROR")
        }
        .map { snapsot ->
        if (snapsot.exists){
            val map = snapsot.value as Map<String, Map<*, *>>
            map.values
                .map { DailyAchievements.toDailyAchievements(it) }
                .reduce { acc, item ->
                    DailyAchievements(
                        steps = acc.steps + item.steps,
                        kcal = acc.kcal + item.kcal,
                        distance = acc.distance + item.distance,
                        duration = acc.duration + item.duration
                    )
                }
        }else{
            null
        }
    }

    override suspend fun saveTodayAchievements(data: DailyAchievements) {
        val todayMillis = LocalDateTime.now().toDayMilliseconds()
        path.child(todayMillis.toString()).setValue(data.toMap())
    }

    override fun getAchievementsInRange(
        from: LocalDate,
        to: LocalDate
    ): Flow<List<DateAchievements>> {
        val fromInstant = from.atStartOfDayIn(TimeZone.currentSystemDefault())
        val toInstant = to.atStartOfDayIn(TimeZone.currentSystemDefault())
        val today = Clock.System.now()

        val daysBetween = (toInstant - fromInstant).inWholeDays.toInt() + 1
        val daysFromToday = (today - fromInstant).inWholeDays.toInt()

        val limit = (if (daysFromToday <= 0) 1 else daysFromToday) + 1

        val datesList = Array(size = daysBetween){ index ->
            from.plus(index, DateTimeUnit.DAY).atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
        }

        return path.limitToLast(limit).valueEvents
            .catch { e ->
                Logger.e(tag = "getAchievementsInRange", throwable = e, messageString = "ERROR")
            }
            .map { snapshot ->
            val mapa = if (snapshot.exists){
                snapshot.value as Map<String, Map<*, *>?>
            }else emptyMap()

            datesList.map { date ->
                val key = date.toString()
                val data = mapa[key]
                DateAchievements(
                    date = date.toLocalDateTime().date,
                    achievements = data?.let { DailyAchievements.toDailyAchievements(it) }
                )
            }
        }
    }
}