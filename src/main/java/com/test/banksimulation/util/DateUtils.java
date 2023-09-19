package com.test.banksimulation.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

  public static final String DATE_FORMAT = "yyyy-MM-d";

  public static LocalDateTime parse(LocalDate date) {
    return date.atStartOfDay();
  }

  public static LocalDateTime getStartDate(LocalDateTime date) {
    return LocalDateTime.of(date.toLocalDate(), LocalTime.MIN);
  }

  public static LocalDateTime getEndDate(LocalDateTime date) {
    return LocalDateTime.of(date.toLocalDate(), LocalTime.MAX);
  }

  public static LocalDate toLocalDate(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    return LocalDate.parse(date, formatter);
  }

  public static Date toDate(String fechaVencimiento) {
    SimpleDateFormat dateFormat =
        new SimpleDateFormat("yyyy-MM-dd"); // Define el formato de fecha esperado
    try {
      Date date = dateFormat.parse(fechaVencimiento);
      return date;
    } catch (ParseException e) {
      e.printStackTrace(); // Manejo de errores en caso de que la cadena de fecha no sea válida
      return null;
    }
  }
}
