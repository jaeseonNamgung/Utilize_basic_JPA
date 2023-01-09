package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    // 엔티티 클래스를 직접 사용
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }
    
    // DTO 클래스를 사용
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid  CreateMemberRequest request){
            Member member  = new Member();
            member.setName(request.getName());

            Long id = memberService.join(member);
            return new CreateMemberResponse(id);
    }

    // 회원 수정
    @PutMapping("/api/v2/member/{id}")
    public updateMemberResponse updateMemberV2
            (@PathVariable("id")Long id, @RequestBody @Valid updateMemberRequest request){
        memberService.updateMember(id, request.getName());
        Member member = memberService.findOne(id);
        return new updateMemberResponse(member.getId(), member.getName());
    }

    // 회원 조회
    // 1. 엔티티를 사용할 경우
    @GetMapping("/api/v1/members")
    public List<Member> membersV1(){
        return memberService.findMembers();
    }

    // 2. DTO를 사용할 경우
    @GetMapping("/api/v2/members")
    public MemberListResponse<List<Member>> membersV2(){
            List<Member> member = memberService.findMembers();
            return new MemberListResponse<List<Member>>(member);
    }

    @Data
    @AllArgsConstructor
    static class MemberListResponse<T>{
        private T member;
    }
    

    @Data
    @AllArgsConstructor
    static class updateMemberResponse{
        private Long id;
        private String name;
    }
    @Data
    static class updateMemberRequest{
        @NotEmpty
        private String name;
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
