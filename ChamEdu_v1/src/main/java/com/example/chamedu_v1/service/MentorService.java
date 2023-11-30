package com.example.chamedu_v1.service;
//멘토의 회원가입과 로그인에 필요한 로직을 구현
import com.example.chamedu_v1.config.SecurityUtil;
import com.example.chamedu_v1.config.jwt.TokenProvider;
import com.example.chamedu_v1.data.dto.MentorRequestDto;
import com.example.chamedu_v1.data.dto.MentorResponseDto;
import com.example.chamedu_v1.data.dto.MentorSignUpDto;
import com.example.chamedu_v1.data.dto.TokenDto;
import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.data.repository.MentorRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class MentorService {
    private final MentorRepository mentorRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManagerBuilder managerBuilder;
    private final TokenProvider tokenProvider;
//    Logger logger = LoggerFactory.getLogger(MentorService.class);

    public MentorService(MentorRepository mentorRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                         AuthenticationManagerBuilder managerBuilder , TokenProvider tokenProvider) {
        this.mentorRepository = mentorRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.managerBuilder  = managerBuilder;
        this.tokenProvider = tokenProvider;
    }

//    @Transactional
//    public Mentor join(Mentor mentor) {
//        String rawPassword = mentor.getPassword();
//        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
//        mentor.setPassword(encPassword);
//        Mentor mentorEntity = mentorRepository.save(mentor);
//
//        return mentorEntity;
//    }
    @Transactional
    public MentorResponseDto getMyInfoBySecurity() {
        return new MentorResponseDto((Mentor) mentorRepository.findByUserId(SecurityUtil.getCurrentMember()));
    }

    @Transactional
    public TokenDto login(MentorRequestDto mentorRequestDto) {

        UsernamePasswordAuthenticationToken authenticationToken = mentorRequestDto.toAuthentication();
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        return tokenProvider.generateTokenDto(authentication);
    }


    @Transactional
    public void join(MentorSignUpDto mentorSignUpDto){
        mentorRepository.saveMember(
                mentorSignUpDto.toEntity(
                        bCryptPasswordEncoder.encode(
                                bCryptPasswordEncoder.encode(mentorSignUpDto.getPassword()))));
    }


//    private void validateDuplicateMember(Mentor member) {
//        List<Mentor> findMembers = mentorRepository.findByUserId(member.getName());
//        if(!findMembers.isEmpty()){
//            throw new IllegalStateException("이미 존재하는 이름입니다.");
//        }
//    }
//
//    @Transactional
//    public boolean checkDuplicateId(String userId) {
//        //return mentorRepository.existsByUsername(userId);
//    }
    public List<Mentor> findMembers() {
        return mentorRepository.findAll();
    }

    public Mentor findOne(Long memberId) {
        return mentorRepository.findOne(memberId);
    }
}
