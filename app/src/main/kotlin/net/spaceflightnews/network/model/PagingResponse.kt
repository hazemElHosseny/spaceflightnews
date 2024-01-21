package net.spaceflightnews.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data layer model for PagingResponse that we receive from API
 *
 * Kotlinx Serialization is use to serialize/deserialize model
 *  all fields must have SerialName to avoid fields serialize/deserialize issues
 *  that can be caused by fields renaming when using obfuscation(ProGuard)
 *
 */
@Serializable
data class PagingResponse<T>(
    @SerialName("previous")
    val previous: String?,
    @SerialName("next")
    val next: String?,
    @SerialName("results")
    val results: List<T>
)
