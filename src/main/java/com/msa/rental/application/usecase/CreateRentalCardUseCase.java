package com.msa.rental.application.usecase;

import com.msa.rental.framework.web.request.UserInput;
import com.msa.rental.framework.web.response.RentalCardOutput;

public interface CreateRentalCardUseCase {
    RentalCardOutput createRentalCard(UserInput userInput);
}
