package member.domain;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String name;
    private String phone;

    @Lob
    private String career;
    private Long point = 0L;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Builder
    private Member(String email, String name, String phone, String career) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.career = career;

        this.point = 0L;
        this.status = MemberStatus.ACTIVE;
        this.role = Role.MEMBER;
    }
}
