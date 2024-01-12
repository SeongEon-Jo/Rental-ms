package com.msa.rental.application.usecase;

import com.msa.rental.framework.web.request.UserInput;
import com.msa.rental.framework.web.response.RentalCardOutput;
import com.msa.rental.framework.web.response.RentItemOutput;
import com.msa.rental.framework.web.response.ReturnItemOutput;

import java.util.List;

public interface InquiryUseCase {
    RentalCardOutput getRentalCard(UserInput userInput);
    List<RentItemOutput> getAllRentalItem(UserInput userInput);
    List<ReturnItemOutput> getAllReturnItem(UserInput userInput);
}
