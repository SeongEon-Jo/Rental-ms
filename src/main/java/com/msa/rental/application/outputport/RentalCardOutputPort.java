package com.msa.rental.application.outputport;

import com.msa.rental.domain.model.RentalCard;

import java.util.Optional;

public interface RentalCardOutputPort {
    Optional<RentalCard> loadRentalCard(String userId);
    RentalCard saver(RentalCard rentalCard);
}
