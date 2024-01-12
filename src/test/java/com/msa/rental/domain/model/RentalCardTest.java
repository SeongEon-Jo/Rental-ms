package com.msa.rental.domain.model;

import com.msa.rental.domain.model.vo.IDName;
import com.msa.rental.domain.model.vo.Item;
import com.msa.rental.domain.model.vo.RentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RentalCardTest {

    @Test
    @DisplayName("도서를 사용자에게 대여한다.")
    void rentItem() {
        // given
        Item sampleItem = new Item(1, "도서 제목");
        IDName user = new IDName("id", "사용자");
        RentalCard rentalCard = RentalCard.createRentalCard(user);

        // when
        rentalCard.rentItem(sampleItem);

        // then
        assertThat(rentalCard.getRentalItemList()).isNotEmpty();
    }

    @Test
    @DisplayName("도서를 반납한다.")
    void returnItem() {
        // given
        Item sampleItem = new Item(1, "도서 제목");
        IDName user = new IDName("id", "사용자");
        RentalCard rentalCard = RentalCard.createRentalCard(user);
        rentalCard.rentItem(sampleItem);

        // when
        rentalCard.returnItem(sampleItem, LocalDate.now());

        // then
        assertThat(rentalCard.getRentalItemList()).isEmpty();
        assertThat(rentalCard.getReturnItemList()).isNotEmpty();
    }

    @Test
    @DisplayName("대여한 도서가 연체되면 대여 정지 상태가 된다.")
    void rentalUnavailableIfReturnLate() {
        // given
        Item sampleItem = new Item(1, "도서 제목");
        IDName user = new IDName("id", "사용자");
        RentalCard rentalCard = RentalCard.createRentalCard(user);
        rentalCard.rentItem(sampleItem);


        // when
        rentalCard.overdueItem(sampleItem, 1);
        RentStatus rentStatus = rentalCard.getRentStatus();

        // then
        assertThat(rentStatus).isEqualTo(RentStatus.RENT_UNAVAILABLE);
    }

    @Test
    @DisplayName("연체된 도서를 반납하면 연체일 하루 당 10 포인트가 연체료로 계산된다.")
    void calculateLateFee10PointPerLateDay() {
        // given
        Item sampleItem = new Item(1, "도서 제목");
        IDName user = new IDName("id", "사용자");
        RentalCard rentalCard = RentalCard.createRentalCard(user);
        rentalCard.rentItem(sampleItem);
        int overdueDay = 1;
        rentalCard.overdueItem(sampleItem, overdueDay);

        // when
        rentalCard.returnItem(sampleItem, LocalDate.now());
        long point = rentalCard.getLateFee().getPoint();

        // then
        assertThat(point).isEqualTo(10 * overdueDay);
    }

    @Test
    @DisplayName("포인트로 연체료를 삭감하여 대여 정지 상태를 해제할 수 있다.")
    void makeRentalAvailableByPayingPoint() {
        // given
        Item sampleItem = new Item(1, "도서 제목");
        IDName user = new IDName("id", "사용자");
        RentalCard rentalCard = RentalCard.createRentalCard(user);
        rentalCard.rentItem(sampleItem);

        int overdueDay = 1;
        rentalCard.overdueItem(sampleItem, overdueDay);
        rentalCard.returnItem(sampleItem, LocalDate.now());

        // when
        rentalCard.makeAvailableRental(10);

        // then
        RentStatus rentStatus = rentalCard.getRentStatus();
        assertThat(rentStatus).isEqualTo(RentStatus.RENT_AVAILABLE);
    }

    @Test
    @DisplayName("도서를 모두 반납해야 대여 정지 상태를 해제할 수 있다.")
    void makeRentalAvailableAfterReturnEveryRentalItem() {
        // given
        Item sampleItem1 = new Item(1, "도서 제목");
        Item sampleItem2 = new Item(2, "도서 제목");
        IDName user = new IDName("id", "사용자");
        RentalCard rentalCard = RentalCard.createRentalCard(user);
        rentalCard.rentItem(sampleItem1);
        rentalCard.rentItem(sampleItem2);

        int overdueDay = 1;
        rentalCard.overdueItem(sampleItem1, overdueDay);
        rentalCard.returnItem(sampleItem1, LocalDate.now());

        // when & then
        assertThatThrownBy(() -> rentalCard.makeAvailableRental(10));
    }

}