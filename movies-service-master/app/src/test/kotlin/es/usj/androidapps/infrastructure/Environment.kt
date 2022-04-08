package es.usj.androidapps.infrastructure

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class Environment(private val value: String) {
    LOCAL("LOCAL"),
    TEST("TEST"),
    QA("QA"),
    PRODUCTION("PRODUCTION");

    @JsonValue
    override fun toString(): String {
        return value
    }

    companion object {
        @JsonCreator
        fun fromValue(text: String): Environment {
            for (b in values()) {
                if (b.value == text) {
                    return b
                }
            }
            throw IllegalArgumentException("Unexpected value '$text'")
        }
    }
}
