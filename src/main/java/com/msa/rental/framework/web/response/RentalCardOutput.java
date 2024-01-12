package com.msa.rental.framework.web.response;

import com.msa.rental.domain.model.RentalCard;
import com.msa.rental.domain.model.RentalItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RentalCardOutput {
    private String rentalCardId;
    private String memberId;
    private String memberName;
    private String rentStatus;
    private Long totalLateFee;
    private int totalRentalCnt;
    private int totalReturnCnt;
    private int totalOverdueCnt;

    public static RentalCardOutput from(RentalCard rentalCard) {
        return new RentalCardOutput(
                rentalCard.getRentalCardNo().getNo(),
                rentalCard.getMember().getId(),
                rentalCard.getMember().getName(),
                rentalCard.getRentStatus().name(),
                rentalCard.getLateFee().getPoint(),
                rentalCard.getRentalItemList().size(),
                rentalCard.getReturnItemList().size(),
                (int) rentalCard.getRentalItemList().stream()
                        .filter(RentalItem::isOverdue).count()
        );
    }
}
