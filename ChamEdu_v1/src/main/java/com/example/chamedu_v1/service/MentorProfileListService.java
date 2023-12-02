package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.MentorProfileDto;
import com.example.chamedu_v1.data.repository.MenteeRepository;
import com.example.chamedu_v1.data.repository.ProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MentorProfileListService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MenteeRepository menteeRepository;


    @Transactional
    public Page<MentorProfileDto> getProfileList(Pageable pageable) {
//        if(authentication != null && authentication.getPrincipal() != "anonymousUser"){
//            return profileRepository.findAllByMentor().map(profile->new ProfileListDto(menteeRepository.findByUserId(authentication.getName()).getMenteeId());
//        }
        return profileRepository.findAllByOrderByMentorMentorIdDesc(pageable).map(MentorProfileDto::new);

    }

//    @Transactional
//    public Page<MentorProfileDto> getPopularMentor(Pageable pageable) {
//
//        return profileRepository.findAllByMentorChatCountDesc(pageable).map(MentorProfileDto::new);
//
//    }


}
