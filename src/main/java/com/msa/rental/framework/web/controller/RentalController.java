package com.msa.rental.framework.web.controller;

import com.msa.rental.application.usecase.*;
import com.msa.rental.framework.web.request.ClearOverdueInput;
import com.msa.rental.framework.web.request.UserInput;
import com.msa.rental.framework.web.request.UserItemInput;
import com.msa.rental.framework.web.response.RentItemOutput;
import com.msa.rental.framework.web.response.RentalCardOutput;
import com.msa.rental.framework.web.response.RentalResultOutput;
import com.msa.rental.framework.web.response.ReturnItemOutput;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RentalController {
    private final RentItemUseCase rentItemUseCase;
    private final ReturnItemUseCase returnItemUseCase;
    private final CreateRentalCardUseCase createRentalCardUseCase;
    private final OverdueItemUseCase overdueItemUseCase;
    private final ClearOverdueItemUseCase clearOverdueItemUseCase;
    private final InquiryUseCase inquiryUseCase;

    @ApiOperation(value = "도서카드 생성",notes = "사용자정보 -> 도서카드정보")
    @PostMapping("/rental-card")
    public ResponseEntity<RentalCardOutput> createRentalCard(@RequestBody UserInput userInput) {
        RentalCardOutput createdRentalCard = createRentalCardUseCase.createRentalCard(userInput);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdRentalCard);
    }

    @ApiOperation(value = "도서카드 조회",notes = "사용자정보(id) -> 도서카드정보")
    @GetMapping("/users/{userId}/rental-cards")
    public ResponseEntity<RentalCardOutput> getRentalCard(@PathVariable String userId) {
        RentalCardOutput rentalCard = inquiryUseCase.getRentalCard(new UserInput(userId, ""));

        return ResponseEntity.ok(rentalCard);
    }

    @ApiOperation(value = "대여도서목록 조회",notes = "사용자정보(id) -> 대여도서목록 조회")
    @GetMapping("/users/{userId}/rental-items")
    public ResponseEntity<List<RentItemOutput>> getAllRentItem(@PathVariable String userId) {
        List<RentItemOutput> allRentalItem = inquiryUseCase.getAllRentalItem(new UserInput(userId, ""));

        return ResponseEntity.ok(allRentalItem);
    }

    @ApiOperation(value = "반납도서목록 조회",notes = "사용자정보(id) -> 반납도서목록 조회")
    @GetMapping("/users/{userId}/return-items")
    public ResponseEntity<List<ReturnItemOutput>> getAllReturnItem(@PathVariable String userId) {
        List<ReturnItemOutput> allReturnItem = inquiryUseCase.getAllReturnItem(new UserInput(userId, ""));

        return ResponseEntity.ok(allReturnItem);
    }

    @ApiOperation(value = "대여기능",notes = "사용자정보,아이템정보1 -> 도서카드정보 ")
    @PostMapping("/rental-items/rent")
    public ResponseEntity<RentalCardOutput> rentItem(@RequestBody UserItemInput userItemInput) {
        RentalCardOutput rentalCardOutput = rentItemUseCase.rentItem(userItemInput);

        return ResponseEntity.ok(rentalCardOutput);
    }

    @ApiOperation(value = "반납기능",notes = "사용자정보,아이템정보1 -> 도서카드정보 ")
    @PostMapping("/rental-items/return")
    public ResponseEntity<RentalCardOutput> returnItem(@RequestBody UserItemInput userItemInput) {
        RentalCardOutput rentalCardOutput = returnItemUseCase.returnItem(userItemInput);

        return ResponseEntity.ok(rentalCardOutput);
    }

    @ApiOperation(value = "연체기능",notes = "사용자정보,아이템정보1 -> 도서카드정보 ")
    @PostMapping("/rental-items/overdue")
    public ResponseEntity<RentalCardOutput> overdueItem(@RequestBody UserItemInput userItemInput) {
        RentalCardOutput rentalCardOutput = overdueItemUseCase.overdueItem(userItemInput);

        return ResponseEntity.ok(rentalCardOutput);
    }

    @ApiOperation(value = "연체해제기능",notes = "사용자정보,포인트 -> 도서카드정보 ")
    @PostMapping("/rental-items/overdue/clear")
    public ResponseEntity<RentalResultOutput> clearOverdueItem(@RequestBody ClearOverdueInput clearOverdueInput) {
        RentalResultOutput rentalResultOutput = clearOverdueItemUseCase.clearOverdue(clearOverdueInput);

        return ResponseEntity.ok(rentalResultOutput);
    }
}
