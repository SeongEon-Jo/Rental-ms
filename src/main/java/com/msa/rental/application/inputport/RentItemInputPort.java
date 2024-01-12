package com.msa.rental.application.inputport;

import com.msa.rental.application.outputport.RentalCardOutputPort;
import com.msa.rental.application.usecase.RentItemUseCase;
import com.msa.rental.domain.model.RentalCard;
import com.msa.rental.domain.model.vo.Item;
import com.msa.rental.framework.web.request.UserItemInput;
import com.msa.rental.framework.web.response.RentalCardOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RentItemInputPort implements RentItemUseCase {

    private final RentalCardOutputPort rentalCardOutputPort;

    @Override
    public RentalCardOutput rentItem(UserItemInput userItemInput) {
        RentalCard rentalCard = rentalCardOutputPort.loadRentalCard(userItemInput.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자의 대여 카드를 찾을 수 없습니다."));

        Item newItem = new Item(userItemInput.getItemId(), userItemInput.getItemTitle());
        rentalCard.rentItem(newItem);
        RentalCard savedRentalCard = rentalCardOutputPort.saver(rentalCard);
        return RentalCardOutput.from(savedRentalCard);
    }
}
