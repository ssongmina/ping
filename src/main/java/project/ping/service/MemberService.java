package project.ping.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.ping.apiPayload.exception.GeneralException;
import project.ping.apiPayload.status.ErrorStatus;
import project.ping.converter.MemberConverter;
import project.ping.domain.Member;
import project.ping.dto.JwtDTO;
import project.ping.dto.MemberRequestDTO;
import project.ping.dto.MemberResponseDTO;
import project.ping.repository.MemberRepository;
import project.ping.security.jwt.JwtTokenProvider;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redistemplate;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    String[] adjectives = {
            "귀여운", "상큼한", "시끄러운", "엉뚱한", "달콤한",
            "반짝이는", "깜찍한", "멋진", "순한", "포근한",
            "강력한", "차가운", "따뜻한", "느긋한", "조용한",
            "야무진", "씩씩한", "유쾌한", "반항적인", "엉망진창인",
            "정직한", "똑똑한", "용감한", "사랑스러운", "화려한",
            "재치있는", "쿨한", "따끔한", "긍정적인", "재빠른",
            "기운찬", "해맑은", "까칠한", "반가운", "비밀스러운",
            "심심한", "알쏭달쏭한", "고요한", "폭신폭신한", "새침한",
            "신비로운", "재밌는", "독특한", "엉성한", "정많은",
            "느끼한", "느린", "진지한", "우울한", "도도한",
            "흥분한", "헷갈리는", "짜증나는", "고약한", "까다로운",
            "사나운", "삐딱한", "게으른", "수줍은", "무뚝뚝한",
            "시무룩한", "차분한", "능글맞은", "오만한", "건방진",
            "깐깐한", "용의주도한", "허세부리는", "버릇없는", "엉뚱한",
            "철없는", "소심한", "기발한", "변덕스러운", "어이없는",
            "불타는", "지루한", "야릇한", "뒤죽박죽인", "말없는",
            "겁많은", "장난꾸러기", "감성적인", "독설가인", "논리적인",
            "귀찮은", "거만한", "냉소적인", "친절한", "친근한",
            "다정한", "활기찬", "열정적인", "생기있는", "엉뚱발랄한",
            "엉클어진", "몽환적인", "날카로운", "묘한", "느긋느긋한"
    };

    String[] animalNouns = {
            "강아지", "고양이", "호랑이", "사자", "토끼",
            "여우", "늑대", "곰", "다람쥐", "햄스터",
            "고슴도치", "판다", "너구리", "치타", "표범",
            "기린", "코끼리", "하마", "악어", "돼지",
            "소", "염소", "양", "오리", "백조",
            "거위", "닭", "병아리", "수달", "비버",
            "캥거루", "왈라비", "코알라", "낙타", "말",
            "얼룩말", "하이에나", "도마뱀", "카멜레온", "타조",
            "독수리", "부엉이", "참새", "비둘기", "까마귀",
            "앵무새", "갈매기", "제비", "박쥐", "펭귄",
            "돌고래", "고래", "상어", "문어", "오징어",
            "가재", "게", "불가사리", "해파리", "고등어",
            "참치", "연어", "메기", "붕어", "잉어",
            "금붕어", "개구리", "두꺼비", "도롱뇽", "달팽이",
            "지렁이", "벌", "나비", "잠자리", "개미",
            "모기", "파리", "메뚜기", "귀뚜라미", "베짱이",
            "거미", "진드기", "무당벌레", "사슴벌레", "장수풍뎅이",
            "하늘소", "공작", "고라니", "노루", "사슴",
            "멧돼지", "두루미", "두더지", "뱀", "이구아나",
            "킹크랩", "쥐", "너구리", "담비", "스컹크", "러브버그"
    };

    // 일반 회원가입
    public MemberResponseDTO.MemberJoinResultDTO joinMember(MemberRequestDTO.MemberJoinDTO request){
        // 이메일 중복 여부 확인
        if(memberRepository.existsByEmail(request.getEmail())){
            throw new GeneralException(ErrorStatus.EXIST_EMAIL);
        }

        // DTO -> 엔티티로 변경
        String name = makeRandomName();
        Member member = MemberConverter.toMember(request, name);

        // 비밀번호 암호화
        member.encodePassword(bCryptPasswordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
        return MemberConverter.toJoinResult(member);
    }

    // 랜덤 닉네임 생성
    public String makeRandomName(){
        Random random = new Random();
        int adjIndex = random.nextInt(adjectives.length);
        int nounIndex = random.nextInt(animalNouns.length);

        String adj = adjectives[adjIndex];
        String noun = animalNouns[nounIndex];

        return adj + " " + noun;
    }

    // 이메일 인증번호 전송
    public void sendMail(String email){
        // 6자리 인증번호 생성
        int number = (int)(Math.random() * 90000)+100000;
        // 메시지 생성
        try{
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true,  "UTF-8");
            helper.setSubject("[Ping] 이메일 인증 번호");
            helper.setTo(email);
            helper.setFrom(new InternetAddress("ping@ping.com", "ping", "UTF-8"));
            String body = "";
            body += "<h3>" + "요청하신 인증번호입니다."+"</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>" + "화면으로 돌아가 인증번호를 입력해주세요" + "</h3>";
            body += "<h3>" + "감사합니다" + "</h3>";
            helper.setText(body, true);

            javaMailSender.send(message);
        } catch(MessagingException | UnsupportedEncodingException e){
            log.error("Error Sending email", e);
        }

        // redis에 인증번호 3분간 저장
        ValueOperations<String, Object> ops = redistemplate.opsForValue();
        ops.set("EmailCode"+email, number+"", 180, TimeUnit.SECONDS);
    }

    // 이메일 인증번호 확인
    public void verifyMail(String email, String code){
        ValueOperations<String, Object> ops = redistemplate.opsForValue();
        String emailCode = (String) ops.get("EmailCode"+email);
        if(emailCode == null){
            throw new GeneralException(ErrorStatus.EXPIRE_EMAIL_CODE);
        }
        if(!code.equals(emailCode)){
            throw new GeneralException(ErrorStatus.WRONG_EMAIL_VERIFICATOIN);
        }
    }

    // 일반 로그인
    public HttpHeaders loginMember(MemberRequestDTO.MemberJoinDTO request) {
        // 해당 이메일 존재 여부 및 비밀번호 확인
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_EXIST_EMAIL));

        if(!bCryptPasswordEncoder.matches(request.getPassword(), member.getPassword())){
            throw new GeneralException(ErrorStatus.WRONG_PASSWORD);
        }

        // 액세스 토큰 및 리프레시 토큰을 응답으로 전송
        JwtDTO jwtDTO = jwtTokenProvider.createToken(member.getId(), member.getEmail());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer " + jwtDTO.getAccessToken());
        headers.add("Refresh-Token", jwtDTO.getRefreshToken());

        // 리프레시 토큰은 redis에 저장해야함
        ValueOperations<String, Object> ops = redistemplate.opsForValue();
        ops.set("refresh-token " + member.getEmail(), jwtDTO.getRefreshToken(), 7, TimeUnit.DAYS);

        return headers;
    }

}
