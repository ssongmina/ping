package project.ping.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.ping.domain.Member;
import project.ping.repository.MemberRepository;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // 사용자 이메일을 통해 사용자의 정보를 가져오는 메서드
    @Override
    public MemberDetail loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isEmpty()) throw new UsernameNotFoundException(email);
        return MemberDetail.createMemberDetail(member.get());
    }

}
