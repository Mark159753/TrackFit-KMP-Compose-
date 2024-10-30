package org.track.fit.auth.states

import androidx.compose.runtime.Stable

@Stable
interface IsCorrect{

    fun isCorrect():Boolean
}

@Stable
interface IsAllCorrectState {

    fun checkIsAllCorrect(vararg isCorrect: IsCorrect):Boolean{
        var isAllCorrect = true
        isCorrect.forEach {
            if (!it.isCorrect()){
                isAllCorrect = false
            }
        }
        return isAllCorrect
    }
}