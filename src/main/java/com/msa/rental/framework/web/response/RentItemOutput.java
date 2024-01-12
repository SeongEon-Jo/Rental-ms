package com.msa.rental.framework.web.response;

import com.msa.rental.domain.model.RentalItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RentItemOutput {
    private int itemNo;
    private String itemTitle;
    private LocalDate rentDate;
    private boolean overdue;
    private LocalDate overdueDate;

    public static RentItemOutput from(RentalItem rentalItem) {
        return new RentItemOutput(
                rentalItem.getItem().getNo(),
                rentalItem.getItem().getTitle(),
                rentalItem.getRentDate(),
                rentalItem.isOverdue(),
                rentalItem.getOverdueDate()
        );

    }
}
