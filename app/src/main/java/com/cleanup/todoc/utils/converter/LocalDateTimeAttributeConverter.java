package com.cleanup.todoc.utils.converter;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;


public class LocalDateTimeAttributeConverter {

    @TypeConverter
    public static LocalDateTime fromTimestamp(String localTime) {
        return localTime == null ? null : LocalDateTime.parse(localTime);
    }

    @TypeConverter
    public static String timeToTimestamp(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.toString();
    }
}


