package com.msa.rental.domain.model;

import com.msa.rental.domain.model.vo.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RentalCard {
    @EmbeddedId
    private RentalCardNo rentalCardNo;
    @Embedded
    private IDName member;
    private RentStatus rentStatus;
    @Embedded
    private LateFee lateFee;
    @ElementCollection
    private List<RentalItem> rentalItemList = new ArrayList<>();
    @ElementCollection
    private List<ReturnItem> returnItemList = new ArrayList<>();

    private void addRentalItem(RentalItem rentalItem) {
        this.rentalItemList.add(rentalItem);
    }

    private void removeRentalItem(RentalItem rentalItem) {
        this.rentalItemList.remove(rentalItem);
    }

    private void addReturnItem(ReturnItem returnItem) {
        this.returnItemList.add(returnItem);
    }

    public static RentalCard createRentalCard(IDName creator) {
        RentalCard rentalCard = new RentalCard();
        rentalCard.setRentalCardNo(RentalCardNo.createRentalCardNo());
        rentalCard.setMember(creator);
        rentalCard.setRentStatus(RentStatus.RENT_AVAILABLE);
        rentalCard.setLateFee(LateFee.createLateFee());

        return rentalCard;
    }

    public RentalCard rentItem(Item item) {
        checkRentalAvailable();
        RentalItem rentalItem = RentalItem.createRentalItem(item);
        addRentalItem(rentalItem);

        return this;
    }

    private void checkRentalAvailable() {
        // 1인당 최대 5권 대여가능
        // 1권이라도 연체되면 대여 불가
        if (this.rentStatus == RentStatus.RENT_UNAVAILABLE) throw new IllegalStateException("대여 불가 상태입니다.");
        if (this.rentalItemList.size() > 5) throw new IllegalStateException("이미 5권을 대여하였습니다.");
    }

    public RentalCard returnItem(Item item, LocalDate returnDate) {
        RentalItem rentalItem = this.rentalItemList
                .stream()
                .filter(i -> i.getItem().equals(item))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템은 대여되지 않았습니다."));

        // 연체료 계산
        calculateLateFee(rentalItem, returnDate);
        this.addReturnItem(ReturnItem.createReturnItem(rentalItem));
        this.removeRentalItem(rentalItem);

        return this;
    }

    private void calculateLateFee(RentalItem rentalItem, LocalDate returnDate) {
        if (rentalItem.getOverdueDate().isAfter(returnDate)) return;
        long latePoint = Period.between(rentalItem.getOverdueDate(), returnDate).getDays() * 10L;
        this.lateFee = this.lateFee.addPoint(latePoint);
    }

    // 연체 처리
    // 실제로 필요는 없는 코드 (테스트 용도)
    public RentalCard overdueItem(Item item, int overdueDay) {
        RentalItem rentalItem = this.rentalItemList
                .stream()
                .filter(i -> i.getItem().equals(item))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템은 대여되지 않았습니다."));

        rentalItem.setOverdue(true);
        this.rentStatus = RentStatus.RENT_UNAVAILABLE;

        rentalItem.setOverdueDate(LocalDate.now().minusDays(overdueDay));
        return this;
    }

    public long makeAvailableRental(long point) {
        if (this.getLateFee().getPoint() != point) throw new IllegalArgumentException("해당 포인트로 연체를 해제할 수 없습니다.");
        if (!this.rentalItemList.isEmpty()) throw new IllegalStateException("모든 도서가 반납되어야만 연체를 해제할 수 있습니다.");
        this.setLateFee(lateFee.removePoint(point));
        if (this.lateFee.getPoint() == 0) this.rentStatus = RentStatus.RENT_AVAILABLE;

        return this.lateFee.getPoint();
    }
}
