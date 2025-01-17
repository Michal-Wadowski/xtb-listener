package net.ddns.wadosm.process.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Jacksonized
public class TickRecordDto {
    private BigDecimal ask;
    private BigDecimal bid;
    private Long askVolume;
    private Long bidVolume;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal spreadRaw;
    private BigDecimal spreadTable;
    private String symbol;
    private int quoteId;
    private int level;
    private Instant timestamp;
}
