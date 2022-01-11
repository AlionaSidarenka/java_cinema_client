package cinema.Model;

import java.math.BigDecimal;

enum PriceRatio {
    HIGH(BigDecimal.valueOf(1.2)),
    MEDIUM(BigDecimal.valueOf(1)),
    LOW(BigDecimal.valueOf(0.8));

    private final BigDecimal ratio;

    PriceRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public BigDecimal getRatio() {
        return ratio;
    }
}
