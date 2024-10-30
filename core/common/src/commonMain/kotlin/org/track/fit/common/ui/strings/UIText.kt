package org.track.fit.common.ui.strings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Immutable
sealed class UIText {
    data class DynamicString(
        val value:String
    ): UIText()

    @Immutable
    data class ResString(
        val res:StringResource,
        val args:Array<Any> = arrayOf()
    ): UIText() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true

            if (other.hashCode() != this.hashCode()) return false
            return true
        }

        override fun hashCode(): Int {
            var result = res.hashCode()
            result = 31 * result + args.contentHashCode()
            return result
        }
    }

    @Composable
    fun asString():String = when(this){
        is DynamicString -> value
        is ResString -> stringResource(res, *args)
    }

    suspend fun getString():String = when(this){
        is DynamicString -> value
        is ResString -> getString(res, *args)
    }
}