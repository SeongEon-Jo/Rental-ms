package com.msa.rental.framework.jpaadapter;

import com.msa.rental.domain.model.RentalCard;
import com.msa.rental.domain.model.vo.RentalCardNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RentalCardRepository extends JpaRepository<RentalCard, RentalCardNo> {

    @Query("select m from RentalCard m where m.member.id = :memberId")
    Optional<RentalCard> findByMemberId(@Param("memberId") String memberId);

    @Query("select m from RentalCard m where m.rentalCardNo.no = :rentalCardNo")
    Optional<RentalCard> findByRentalCardNo(@Param("rentalCardNo") long rentalCardNo);
}
