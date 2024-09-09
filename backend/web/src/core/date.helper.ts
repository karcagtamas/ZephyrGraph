export enum Month {
  JAN,
  FEB,
  MAR,
  APR,
  MAY,
  JUN,
  JUL,
  AUG,
  SEP,
  OCT,
  NOV,
  DEC,
}

export interface LocalDate {
  year: number;
  month: Month;
  day: number;
}

export interface LocalDateTime extends LocalDate {
  hour: number;
  minutes: number;
  seconds: number;
}

abstract class DateConverter<T> {
  abstract to(date: Date): T;

  abstract from(date: T): Date;

  abstract toString(date: T): string;
}

class LocalDateConverter extends DateConverter<LocalDate> {
  override to(date: Date): LocalDate {
    return {
      year: date.getFullYear(),
      month: date.getMonth() as Month,
      day: date.getDay(),
    };
  }

  override from(date: LocalDate): Date {
    return new Date(date.year, date.month, date.day);
  }

  override toString(date: LocalDate): string {
    return `${date.year}-${date.month}-${date.day}`;
  }
}

class LocalDateTimeConverter extends DateConverter<LocalDateTime> {
  private localDateConverter = new LocalDateConverter();

  override to(date: Date): LocalDateTime {
    return {
      ...this.localDateConverter.to(date),
      hour: date.getHours(),
      minutes: date.getMinutes(),
      seconds: date.getSeconds(),
    };
  }

  override from(date: LocalDateTime): Date {
    return new Date(
      date.year,
      date.month,
      date.day,
      date.hour,
      date.minutes,
      date.seconds
    );
  }

  override toString(date: LocalDateTime): string {
    return (
      this.localDateConverter.toString(date) +
      `${date.hour}:${date.minutes}-${date.seconds}`
    );
  }
}

export const localDateConverter = new LocalDateConverter();
export const localDateTimeConverter = new LocalDateTimeConverter();
