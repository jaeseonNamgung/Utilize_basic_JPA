package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

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

    @PutMapping("/api/v2/member/{id}")
    public updateMemberResponse updateMemberV2
            (@PathVariable("id")Long id, @RequestBody @Valid updateMemberRequest request){
        memberService.updateMember(id, request.getName());
        Member member = memberService.findOne(id);
        return new updateMemberResponse(member.getId(), member.getName());
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
