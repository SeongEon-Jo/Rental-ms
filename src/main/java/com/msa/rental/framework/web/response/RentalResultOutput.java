package com.msa.rental.framework.web.response;

import com.msa.rental.domain.model.RentalCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RentalResultOutput {
    private String userId;
    private String userName;
    private int rentedCnt;
    private long totalLateFee;

    public static RentalResultOutput from(RentalCard rentalCard) {
        return new RentalResultOutput(
                rentalCard.getMember().getId(),
                rentalCard.getMember().getName(),
                rentalCard.getRentalItemList().size(),
                rentalCard.getLateFee().getPoint()
        );

    }
}
