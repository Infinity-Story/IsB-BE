package com.infinity.isbbe.commonUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindIdRequest {
    private String memberName;
    private String memberEmail;
    private String mailType = "gmail";  // 기본값 설정
}