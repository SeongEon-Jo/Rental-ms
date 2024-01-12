package com.msa.rental.application.inputport;

import com.msa.rental.application.outputport.RentalCardOutputPort;
import com.msa.rental.application.usecase.InquiryUseCase;
import com.msa.rental.domain.model.RentalCard;
import com.msa.rental.framework.web.request.UserInput;
import com.msa.rental.framework.web.response.RentItemOutput;
import com.msa.rental.framework.web.response.RentalCardOutput;
import com.msa.rental.framework.web.response.ReturnItemOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InquiryInputPort implements InquiryUseCase {

    private final RentalCardOutputPort rentalCardOutputPort;

    @Override
    public RentalCardOutput getRentalCard(UserInput userInput) {
        RentalCard rentalCard = rentalCardOutputPort.loadRentalCard(userInput.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자의 대여 카드를 찾을 수 없습니다."));

        return RentalCardOutput.from(rentalCard);
    }

    @Override
    public List<RentItemOutput> getAllRentalItem(UserInput userInput) {
        RentalCard rentalCard = rentalCardOutputPort.loadRentalCard(userInput.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자의 대여 카드를 찾을 수 없습니다."));

        return rentalCard.getRentalItemList()
                .stream()
                .map(RentItemOutput::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReturnItemOutput> getAllReturnItem(UserInput userInput) {
        RentalCard rentalCard = rentalCardOutputPort.loadRentalCard(userInput.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자의 대여 카드를 찾을 수 없습니다."));

        return rentalCard.getReturnItemList()
                .stream()
                .map(ReturnItemOutput::from)
                .collect(Collectors.toList());
    }
}
