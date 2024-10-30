package org.track.fit.domain.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.track.fit.domain.usecase.CalcCaloriesUC
import org.track.fit.domain.usecase.CalcDistanceUC
import org.track.fit.domain.usecase.CalcSpeedUC
import org.track.fit.domain.usecase.CalcStepLengthUC
import org.track.fit.domain.usecase.GetStepLengthUC
import org.track.fit.domain.usecase.SignInUC
import org.track.fit.domain.usecase.SignUpUC
import org.track.fit.domain.usecase.statistics.GetStatisticsByDataType
import org.track.fit.domain.usecase.statistics.GetStatisticsFor
import org.track.fit.domain.usecase.statistics.GetStatisticsForThisWeek
import org.track.fit.domain.usecase.statistics.GetStatisticsForThisYear

val useCaseModule = module {
    includes(platformUseCaseModule)

    factoryOf(::SignInUC)
    factoryOf(::SignUpUC)

    factoryOf(::CalcSpeedUC)
    factoryOf(::CalcCaloriesUC)
    factoryOf(::CalcDistanceUC)
    factoryOf(::CalcStepLengthUC)
    factoryOf(::GetStepLengthUC)

    factoryOf(::GetStatisticsForThisWeek)
    factoryOf(::GetStatisticsForThisYear)
    factoryOf(::GetStatisticsFor)
    factoryOf(::GetStatisticsByDataType)
}