package net.spaceflightnews.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

/**
 * extension function that converts server date time string to LocalDateTime
 * @return LocalDateTime it can be null if it failed to parse the string.
 *
 */
fun String.serverDateTimeWithToDate(): LocalDateTime? {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    return try {
        LocalDateTime.parse(this, formatter)
    } catch (e: DateTimeParseException) {
        return null
    }
}

/**
 * extension function that converts LocalDateTime to a user readable string
 * @return user readable string in this format ex. 19 Feb 2023 14:30
 *
 */
fun LocalDateTime.formatToReadable(): String {
    return DateTimeFormatter
        .ofPattern("dd MMM yyyy HH:mm", Locale.getDefault())
        .format(this)
}
