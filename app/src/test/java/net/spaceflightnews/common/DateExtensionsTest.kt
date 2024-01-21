package net.spaceflightnews.common

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class DateExtensionsTest {
    @Test
    fun `test parsing with a valid date string should return correct datetime`() {
        val dateString = "2024-01-18T12:34:56Z"
        val result = dateString.serverDateTimeWithToDate()
        val expected = LocalDateTime.of(2024, 1, 18, 12, 34, 56)
        assertEquals(expected, result)
    }

    @Test
    fun `test parsing with a invalid date string should return null`() {
        val invalidDateString = "invalid date"
        val result = invalidDateString.serverDateTimeWithToDate()
        assertNull(result)
    }

    @Test
    fun `test parsing with a incorrect date format string should return null`() {
        val dateString = "2024-01-18 12:34:56"
        val result = dateString.serverDateTimeWithToDate()
        assertNull(result)
    }

    @Test
    fun `test formatting constant date time to readable format`() {
        val dateTime = LocalDateTime.of(2024, 1, 18, 12, 34)
        val result = dateTime.formatToReadable()
        val expected = "18 Jan 2024 12:34"
        assertEquals(expected, result)
    }

    @Test
    fun `test formatting now date time to readable format`() {
        val currentDateTime = LocalDateTime.now()
        val result = currentDateTime.formatToReadable()
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm", Locale.getDefault())
        val expected = formatter.format(currentDateTime)
        assertEquals(expected, result)
    }
}
