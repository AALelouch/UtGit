package com.ut.market.service.request.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDateSaleRequest {

    private String reportName;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    @Schema(type = "string", pattern = "yyyy/MM/dd HH:mm", example = "2024/10/30 03:00")
    private LocalDateTime reportDate;

}
