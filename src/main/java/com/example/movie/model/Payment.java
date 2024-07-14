package com.example.movie.model;

import com.example.movie.enums.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Payment {
    private Long id;
    private Integer amount;
    private PaymentMode paymentMode;
    private Integer transactionid;
}
