package com.msa.rental.framework.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserItemInput {
    private String userName;
    private String userId;
    private int itemId;
    private String itemTitle;
}
