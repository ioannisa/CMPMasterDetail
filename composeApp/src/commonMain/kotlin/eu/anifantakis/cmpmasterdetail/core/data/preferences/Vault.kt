package eu.anifantakis.cmpmasterdetail.core.data.preferences

expect class Vault {
    inline fun <reified T> getDirect(key: String, defaultValue: T, encrypted: Boolean = true): T
    inline fun <reified T> putDirect(key: String, value: T, encrypted: Boolean = true)

    suspend inline fun <reified T> get(key: String, defaultValue: T, encrypted: Boolean = true): T
    suspend inline fun <reified T> put(key: String, value: T, encrypted: Boolean = true)

    suspend fun delete(key: String)
    fun deleteDirect(key: String)
}
