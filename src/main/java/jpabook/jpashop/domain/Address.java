package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
/*
* 값 타입은 변경 불가능하게 설계해야 한다.
* @Setter를 제거하고, 생성자에서 값은 모두 초기화해서 변경 불가능한 클래스를 만들어야 한다.
* JPA 스펙상 엔티티나 임베디드 타입(@Embeddable)은 자바 기본 생성자(default constructor)를
* public 또는 protected로 설정해야 한다. public 으로 두는 것 보다 protected로 설정하는 것이
* 그나마 더 안전하다.
* */
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address(){}

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
