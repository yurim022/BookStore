package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipzode;

    protected Address() {
    }//JPA 제약 사항...기본생성자는 protected or public으로 해줘야됨

    public Address(String city, String street, String zipzode) {
        this.city = city;
        this.street = street;
        this.zipzode = zipzode;
    }
}
