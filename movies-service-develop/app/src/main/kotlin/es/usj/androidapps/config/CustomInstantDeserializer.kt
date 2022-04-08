package es.usj.androidapps.config

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonTokenId
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.threetenbp.DecimalUtils
import com.fasterxml.jackson.datatype.threetenbp.deser.ThreeTenDateTimeDeserializerBase
import com.fasterxml.jackson.datatype.threetenbp.function.BiFunction
import com.fasterxml.jackson.datatype.threetenbp.function.Function
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.Temporal
import org.threeten.bp.temporal.TemporalAccessor
import java.io.IOException
import java.math.BigDecimal

/**
 * Deserializer for ThreeTen temporal [Instant]s, [OffsetDateTime], and [ZonedDateTime]s.
 * Adapted from the jackson threetenbp InstantDeserializer to add support for deserializing rfc822 format.
 *
 * @author Nick Williams
 */
class CustomInstantDeserializer<T : Temporal?> : ThreeTenDateTimeDeserializerBase<T> {
    private val fromMilliseconds: Function<FromIntegerArguments, T>
    private val fromNanoseconds: Function<FromDecimalArguments, T>
    private val parsedToValue: Function<TemporalAccessor, T>
    private val adjust: BiFunction<T, ZoneId?, T>

    protected constructor(
        supportedType: Class<T>?,
        parser: DateTimeFormatter?,
        parsedToValue: Function<TemporalAccessor, T>,
        fromMilliseconds: Function<FromIntegerArguments, T>,
        fromNanoseconds: Function<FromDecimalArguments, T>,
        adjust: BiFunction<T, ZoneId?, T>?
    ) : super(supportedType, parser) {
        this.parsedToValue = parsedToValue
        this.fromMilliseconds = fromMilliseconds
        this.fromNanoseconds = fromNanoseconds
        this.adjust = adjust
            ?: BiFunction { t, _ -> t }
    }

    @Suppress("UNCHECKED_CAST")
    protected constructor(
        base: CustomInstantDeserializer<T>,
        f: DateTimeFormatter?
    ) : super(base.handledType() as Class<T>?, f) {
        parsedToValue = base.parsedToValue
        fromMilliseconds = base.fromMilliseconds
        fromNanoseconds = base.fromNanoseconds
        adjust = base.adjust
    }

    override fun withDateFormat(dtf: DateTimeFormatter): ThreeTenDateTimeDeserializerBase<T>? {
        return if (dtf === _formatter) {
            this
        } else CustomInstantDeserializer(this, dtf)
    }

    @Throws(IOException::class)
    override fun deserialize(parser: JsonParser, context: DeserializationContext): T? {
        //NOTE: Timestamps contain no timezone info, and are always in configured TZ. Only
        //string values have to be adjusted to the configured TZ.
        when (parser.currentTokenId()) {
            JsonTokenId.ID_NUMBER_FLOAT -> {
                val value: BigDecimal = parser.decimalValue
                val seconds: Long = value.toLong()
                val nanoseconds: Int = DecimalUtils.extractNanosecondDecimal(value, seconds)
                return fromNanoseconds.apply(
                    FromDecimalArguments(
                        seconds, nanoseconds, getZone(context)
                    )
                )
            }
            JsonTokenId.ID_NUMBER_INT -> {
                val timestamp = parser.longValue
                return if (context.isEnabled(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)) {
                    fromNanoseconds.apply(
                        FromDecimalArguments(
                            timestamp, 0, getZone(context)
                        )
                    )
                } else fromMilliseconds.apply(
                    FromIntegerArguments(
                        timestamp, getZone(context)
                    )
                )
            }
            JsonTokenId.ID_STRING -> {
                var string = parser.text.trim { it <= ' ' }
                if (string.isEmpty()) {
                    return null
                }
                if (string.endsWith("+0000")) {
                    string = string.substring(0, string.length - 5) + "Z"
                }
                val value: T
                try {
                    val acc: TemporalAccessor = _formatter.parse(string)
                    value = parsedToValue.apply(acc)!!
                    if (context.isEnabled(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)) {
                        return adjust.apply(value, getZone(context))
                    }
                } catch (e: DateTimeException) {
                    throw _peelDTE(e)
                }
                return value
            }
        }

        throw context.reportBadDefinition(Any::class.java, "Expected type float, integer, or string.")
    }

    private fun getZone(context: DeserializationContext): ZoneId? {
        // Instants are always in UTC, so don't waste compute cycles
        return if (_valueClass === Instant::class.java) null else DateTimeUtils.toZoneId(context.timeZone)
    }

    class FromIntegerArguments(val value: Long, val zoneId: ZoneId?)

    class FromDecimalArguments(val integer: Long, val fraction: Int, val zoneId: ZoneId?)

    companion object {
        val INSTANT: CustomInstantDeserializer<Instant> = CustomInstantDeserializer(
            Instant::class.java, DateTimeFormatter.ISO_INSTANT,
            { temporalAccessor -> Instant.from(temporalAccessor) },
            { a -> Instant.ofEpochMilli(a.value) },
            { a -> Instant.ofEpochSecond(a.integer, a.fraction.toLong()) },
            null
        )
        val OFFSET_DATE_TIME: CustomInstantDeserializer<OffsetDateTime> = CustomInstantDeserializer(
            OffsetDateTime::class.java, DateTimeFormatter.ISO_OFFSET_DATE_TIME,
            { temporalAccessor -> OffsetDateTime.from(temporalAccessor) },
            { a -> OffsetDateTime.ofInstant(Instant.ofEpochMilli(a.value), a.zoneId) },
            { a -> OffsetDateTime.ofInstant(Instant.ofEpochSecond(a.integer, a.fraction.toLong()), a.zoneId) }
        ) { d, z -> d.withOffsetSameInstant(z?.rules?.getOffset(d.toLocalDateTime())) }

        val ZONED_DATE_TIME: CustomInstantDeserializer<ZonedDateTime> = CustomInstantDeserializer(
            ZonedDateTime::class.java, DateTimeFormatter.ISO_ZONED_DATE_TIME,
            { temporalAccessor -> ZonedDateTime.from(temporalAccessor) },
            { a -> ZonedDateTime.ofInstant(Instant.ofEpochMilli(a.value), a.zoneId) },
            { a -> ZonedDateTime.ofInstant(Instant.ofEpochSecond(a.integer, a.fraction.toLong()), a.zoneId) }
        ) { zonedDateTime, zoneId -> zonedDateTime.withZoneSameInstant(zoneId) }

        private const val serialVersionUID = 1L
    }

    override fun withLeniency(leniency: Boolean?): ThreeTenDateTimeDeserializerBase<T> {
        return this
    }

    override fun withShape(shape: JsonFormat.Shape?): ThreeTenDateTimeDeserializerBase<T> {
        return this
    }
}