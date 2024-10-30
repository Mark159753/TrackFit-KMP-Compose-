package org.track.fit.ui.util.language

import kotlinx.coroutines.flow.Flow
import org.track.fit.common.constants.AppLanguage

interface LanguageManager {

    val currentLng:Flow<AppLanguage>

    suspend fun changeLng(lng:AppLanguage)

    suspend fun saveInitLng(systemLng:AppLanguage?)
}