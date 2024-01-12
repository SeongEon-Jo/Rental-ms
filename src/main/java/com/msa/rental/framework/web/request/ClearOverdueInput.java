package com.msa.rental.framework.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClearOverdueInput {
    private String userId;
    private String userName;
    private int point;
}
