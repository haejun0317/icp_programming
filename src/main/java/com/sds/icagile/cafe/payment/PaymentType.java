package com.sds.icagile.cafe.payment;

import com.sds.icagile.cafe.exception.NotFoundException;

import java.util.stream.Stream;

public enum PaymentType {
    CASH(1), CARD(2), MILEAGE(3);

    private int code;

    PaymentType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PaymentType fromCode(int code) {
        return Stream.of(values())
                .filter(type -> type.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("invlid PaymentType code"));
    }
}
