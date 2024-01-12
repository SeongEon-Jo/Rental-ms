package com.msa.rental.domain.model;

import com.msa.rental.domain.model.vo.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.time.LocalDate;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RentalItem {
    @Embedded
    private Item item;
    private LocalDate rentDate;
    private boolean overdue;
    private LocalDate overdueDate;

    public static RentalItem createRentalItem(Item item) {
        return new RentalItem(
                item,
                LocalDate.now(),
                false,
                LocalDate.now().plusWeeks(2)
        );
    }
}
