package org.track.fit.data.models

data class RouteModel(
    val id:String,
    val route:List<Location>
){
    fun toMap() = mapOf(
        "id" to id,
        "route" to route.map { it.toMap() }
    )

}
