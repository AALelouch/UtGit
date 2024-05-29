package com.ut.market.service.response.sale;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDateSaleResponse implements Serializable {
    private String reportName;
    private LocalDateTime dateStart;
    private List<SaleResponse> saleResponses;
    private Float totalOfDate;
}
