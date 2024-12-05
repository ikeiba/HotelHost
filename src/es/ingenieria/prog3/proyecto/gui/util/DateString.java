package es.ingenieria.prog3.proyecto.gui.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//IAG: CHATGPT (Toda la clase)
//Modificación: Si
public class DateString {

    // Method to convert a timestamp to a formatted date-time string in UTC
    public static String formatTimestamp(long timestamp, String pattern) {
        return formatTimestamp(timestamp, pattern, ZoneId.of("UTC"));
    }

    // Method to convert a timestamp to a formatted date-time string with a custom ZoneId
    public static String formatTimestamp(long timestamp, String pattern, ZoneId zone) {
        try {
            // Validate the pattern by attempting to create a formatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            // Convert to LocalDateTime in the specified ZoneId
            LocalDateTime dateTime = Instant.ofEpochMilli(timestamp)
                                            .atZone(zone)
                                            .toLocalDateTime();

            // Return the formatted date-time string
            return dateTime.format(formatter);
        } catch (IllegalArgumentException | DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date-time pattern: " + pattern, e);
        }
    }
    
    public static long convertDateToLong(String dateString) {
        try {
            LocalDate date = LocalDate.parse(dateString);
            return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Please use 'year-month-day' format.", e);
        }
    }
}