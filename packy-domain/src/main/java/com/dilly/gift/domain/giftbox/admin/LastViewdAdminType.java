package com.dilly.gift.domain.giftbox.admin;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.dilly.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LastViewdAdminType {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private AdminType adminType;

    public static LastViewdAdminType of(Member member, AdminType adminType) {
        return LastViewdAdminType.builder()
            .member(member)
            .adminType(adminType)
            .build();
    }
}
