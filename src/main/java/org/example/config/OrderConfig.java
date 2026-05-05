package org.example.config;

import java.math.BigDecimal;

public record OrderConfig(String paths, BigDecimal cost, BigDecimal discount, BigDecimal discountStep) {
}
