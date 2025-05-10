package eu.anifantakis.cmpmasterdetail.core.presentation

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T : Any> SavedStateHandle.getMutableStateFlow(
    defaultValue: T,
    key: String? = null,
    coroutineScope: CoroutineScope,
    transientProperties: Set<String> = emptySet(),
): PropertyDelegateProvider<Any, ReadOnlyProperty<Any, MutableStateFlow<T>>> {
    return PropertyDelegateProvider { owner, property ->

        // build a stable key for SavedStateHandle,
        val viewModelClassName = owner.let { it::class.simpleName } ?: "UnknownViewModel"
        val actualKey = key ?: "${viewModelClassName}_${property.name}"

        val serializer = serializer<T>()
        val json = Json { ignoreUnknownKeys = true }

        // ---------- initial value ----------
        val initial: T = get<String>(actualKey)?.let { stored ->
            if (transientProperties.isEmpty()) {
                // no transients in list, just decode the json
                runCatching { json.decodeFromString(serializer, stored) }
                    .getOrElse { defaultValue }
            } else {
                // transients in the list, merge with defaults
                val saved   = json.parseToJsonElement(stored).jsonObject
                val fresh   = json.encodeToJsonElement(serializer, defaultValue).jsonObject

                // rebuild JSON: keep everything except the transient keys,
                // then add the default values for those keys
                val patched = buildJsonObject {
                    saved.forEach { (k, v) -> if (k !in transientProperties) put(k, v) }
                    transientProperties.forEach { k -> fresh[k]?.let { put(k, it) } }
                }

                runCatching { json.decodeFromJsonElement(serializer, patched) }
                    .getOrElse { defaultValue }
            }
        } ?: defaultValue

        // ---------- Persist each StateFlow emission into SavedStateHandle ----------
        MutableStateFlow(initial).also { flow ->
            flow.onEach { value ->
                // initially encode all properties
                val element = json.encodeToJsonElement(serializer, value)
                //  then remove transient ones if "transientProperties" is not empty
                val cleanedObj = (element as? JsonObject)
                    ?.takeIf { transientProperties.isNotEmpty() }
                    ?.let { JsonObject(it.filterKeys { k -> k !in transientProperties }) }
                    ?: element

                // return the encoded value as SavedStateHandle key/value
                set(actualKey, cleanedObj.toString())
            }.launchIn(coroutineScope)
        }.let { ReadOnlyProperty { _, _ -> it } }
    }
}