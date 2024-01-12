package com.msa.rental.framework.web.response;

import com.msa.rental.domain.model.vo.ReturnItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReturnItemOutput {
    private int itemNo;
    private String itemTitle;
    private LocalDate returnDate;

    public static ReturnItemOutput from(ReturnItem returnItem) {
        return new ReturnItemOutput(
                returnItem.getRentalItem().getItem().getNo(),
                returnItem.getRentalItem().getItem().getTitle(),
                returnItem.getReturnDate()
        );

    }
}
