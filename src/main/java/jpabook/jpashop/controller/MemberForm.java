package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "이름은 필수입니다.")
    private String name;
    private String city;
    private String street;
    private String zipcode;

}

//엔티티 자체로 사용하기 보다 Form 형태 (DTO) 를 사용하는 것 권장
//요구사항이 복잡하기 때문에, 엔티티가 화면에 종속적이게 될 수 있음

