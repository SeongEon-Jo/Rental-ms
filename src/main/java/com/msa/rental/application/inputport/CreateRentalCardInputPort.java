package com.msa.rental.application.inputport;

import com.msa.rental.application.outputport.RentalCardOutputPort;
import com.msa.rental.application.usecase.CreateRentalCardUseCase;
import com.msa.rental.domain.model.RentalCard;
import com.msa.rental.domain.model.vo.IDName;
import com.msa.rental.framework.web.request.UserInput;
import com.msa.rental.framework.web.response.RentalCardOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateRentalCardInputPort implements CreateRentalCardUseCase {

    private final RentalCardOutputPort rentalCardOutputPort;

    @Override
    public RentalCardOutput createRentalCard(UserInput owner) {
        IDName user = new IDName(owner.getUserId(), owner.getUserName());
        RentalCard rentalCard = RentalCard.createRentalCard(user);

        RentalCard savedRentalCard = rentalCardOutputPort.saver(rentalCard);

        return RentalCardOutput.from(savedRentalCard);
    }
}
