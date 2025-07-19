package com.gabrieldsrod.cacheta.converter;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Converters {

    // LocalDate
    @TypeConverter
    public static LocalDate fromStringToDate(String value) {
        return value == null ? null : LocalDate.parse(value);
    }

    @TypeConverter
    public static String fromDateToString(LocalDate date) {
        return date == null ? null : date.toString();
    }

    // LocalDateTime
    @TypeConverter
    public static LocalDateTime fromTimestamp(String value) {
        return value == null ? null : LocalDateTime.parse(value);
    }

    @TypeConverter
    public static String dateTimeToTimestamp(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.toString(); // ISO-8601
    }
}
