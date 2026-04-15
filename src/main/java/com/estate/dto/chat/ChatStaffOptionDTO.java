package com.estate.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatStaffOptionDTO {
    private Long staffId;
    private String fullName;
    private String phone;
    private String image;
}
