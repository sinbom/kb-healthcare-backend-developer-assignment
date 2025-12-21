package com.kb.healthcare.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Map;

import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_UP;
import static java.time.Duration.between;
import static org.springframework.util.Assert.notNull;

public class TimeProportionalSplitUtil {

    public static Map<LocalDate, BigDecimal> splitByDay(
            LocalDateTime from,
            LocalDateTime to,
            BigDecimal totalValue
    ) {
        notNull(
                from,
                "시작 날짜가 없습니다."
        );

        notNull(
                to,
                "종료 날짜가 없습니다."
        );

        notNull(
                totalValue,
                "값이 없습니다."
        );

        LocalDate fromDate = from.toLocalDate();

        LocalDate toDate = to.toLocalDate();

        if (!fromDate.plusDays(1).equals(toDate)) {
            throw new UnsupportedOperationException("종료 날짜는 시작 날짜의 하루 뒤로만 입력 가능합니다.");
        }

        if (fromDate.equals(toDate)) {
            return Map.of(
                    fromDate,
                    totalValue
            );
        }

        long totalNanos = between(
                from,
                to
        )
                .toNanos();

        long firstNanos = between(
                from,
                toDate.atStartOfDay()
        )
                .toNanos();

        BigDecimal firstRatio = new BigDecimal(firstNanos)
                .divide(
                        new BigDecimal(totalNanos),
                        DECIMAL128
                );

        BigDecimal firstValue = totalValue.multiply(
                        firstRatio,
                        DECIMAL128
                )
                .setScale(
                        totalValue.scale(),
                        HALF_UP
                );

        BigDecimal secondValue = totalValue.subtract(firstValue);

        return Map.of(
                fromDate, firstValue,
                toDate, secondValue
        );
    }

    public static Map<YearMonth, BigDecimal> splitByMonth(
            LocalDateTime from,
            LocalDateTime to,
            BigDecimal totalValue
    ) {
        notNull(
                from,
                "시작 날짜가 없습니다."
        );

        notNull(
                to,
                "종료 날짜가 없습니다."
        );

        notNull(
                totalValue,
                "값이 없습니다."
        );

        LocalDate fromDate = from.toLocalDate();

        LocalDate toDate = to.toLocalDate();

        YearMonth fromYearMonth = YearMonth.from(from);

        YearMonth toYearMonth = YearMonth.from(to);

        if (!fromDate.plusDays(1).equals(toDate)) {
            throw new UnsupportedOperationException("종료 날짜는 시작 날짜의 하루 뒤로만 입력 가능합니다.");
        }

        if (fromYearMonth.equals(toYearMonth)) {
            return Map.of(
                    fromYearMonth,
                    totalValue
            );
        }

        long totalNanos = between(
                from,
                to
        )
                .toNanos();

        long firstNanos = between(
                from,
                toDate.atStartOfDay()
        )
                .toNanos();

        BigDecimal firstRatio = new BigDecimal(firstNanos)
                .divide(
                        new BigDecimal(totalNanos),
                        DECIMAL128
                );

        BigDecimal firstValue = totalValue.multiply(firstRatio)
                .setScale(
                        totalValue.scale(),
                        HALF_UP
                );

        BigDecimal secondValue = totalValue.subtract(firstValue);

        return Map.of(
                fromYearMonth, firstValue,
                toYearMonth, secondValue
        );
    }

}
