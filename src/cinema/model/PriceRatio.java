package cinema.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
enum PriceRatio {
    HIGH(BigDecimal.valueOf(1.2)),
    MEDIUM(BigDecimal.valueOf(1)),
    LOW(BigDecimal.valueOf(0.8));

    @Getter
    private final BigDecimal ratio;

}
