package com.msa.rental.application.usecase;

import com.msa.rental.framework.web.request.UserItemInput;
import com.msa.rental.framework.web.response.RentalCardOutput;

public interface ReturnItemUseCase {
    RentalCardOutput returnItem(UserItemInput userItemInput);
}
