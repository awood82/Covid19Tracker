package com.example.android.covid19tracker.util

import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Test

class UtilsTest {

    @Test
    fun String_toFloatEx_with_0() {
        val value = "0"

        assertThat(value.toFloatEx(), `is`(0f))
    }

    @Test
    fun String_toFloatEx_with_number() {
        val value = "1234"

        assertThat(value.toFloatEx(), `is`(1234f))
    }

    @Test
    fun String_toFloatEx_with_comma() {
        val value = "1,234"

        assertThat(value.toFloatEx(), `is`(1234f))
    }

    @Test
    fun String_toFloatEx_with_words_defaults_to_0() {
        val value = "N/A"

        assertThat(value.toFloatEx(), `is`(0f))
    }
}