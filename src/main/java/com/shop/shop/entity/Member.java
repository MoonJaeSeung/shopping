package com.shop.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shop.shop.constant.Role;
import com.shop.shop.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_ida")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Coupon> coupon;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.ADMIN);
        return member;
    }

    public void removeCoupon(Long couponId) {
        for (int i = 0; i < coupon.size(); i++) {
            if (coupon.get(i).getId() == couponId)
                coupon.remove(i);
        }
    }

    public void removeAllCoupon(){
        coupon.clear();
    }

}
