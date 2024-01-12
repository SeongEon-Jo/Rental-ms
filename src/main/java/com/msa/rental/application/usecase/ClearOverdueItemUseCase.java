package com.msa.rental.application.usecase;

import com.msa.rental.framework.web.request.ClearOverdueInput;
import com.msa.rental.framework.web.response.RentalResultOutput;

public interface ClearOverdueItemUseCase {
    RentalResultOutput clearOverdue(ClearOverdueInput clearOverdueInput);
}
