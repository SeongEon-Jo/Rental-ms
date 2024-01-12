package com.msa.rental.application.inputport;

import com.msa.rental.application.outputport.RentalCardOutputPort;
import com.msa.rental.application.usecase.ClearOverdueItemUseCase;
import com.msa.rental.domain.model.RentalCard;
import com.msa.rental.framework.web.request.ClearOverdueInput;
import com.msa.rental.framework.web.response.RentalResultOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClearOverdueItemInputPort implements ClearOverdueItemUseCase {

    private final RentalCardOutputPort rentalCardOutputPort;

    @Override
    public RentalResultOutput clearOverdue(ClearOverdueInput clearOverdueInput) {
        RentalCard rentalCard = rentalCardOutputPort.loadRentalCard(clearOverdueInput.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자의 대여 카드를 찾을 수 없습니다."));

        rentalCard.makeAvailableRental(clearOverdueInput.getPoint());

        RentalCard savedRentalCard = rentalCardOutputPort.saver(rentalCard);

        return RentalResultOutput.from(savedRentalCard);
    }
}
