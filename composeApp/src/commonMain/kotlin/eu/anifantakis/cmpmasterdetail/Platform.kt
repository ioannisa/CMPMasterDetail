package eu.anifantakis.cmpmasterdetail

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform