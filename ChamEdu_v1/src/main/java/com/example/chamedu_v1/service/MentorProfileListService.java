package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.MentorProfileDto;
import com.example.chamedu_v1.data.dto.MentorProfileListDto;
import com.example.chamedu_v1.data.entity.Mentee;
import com.example.chamedu_v1.data.entity.Profile;
import com.example.chamedu_v1.data.repository.MenteeRepository;
import com.example.chamedu_v1.data.repository.ProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MentorProfileListService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MenteeRepository menteeRepository;


    @Transactional
    public Page<MentorProfileDto> getProfileList(Pageable pageable) {

        return profileRepository.findAllByOrderByMentorMentorIdDesc(pageable).map(MentorProfileDto::new);

    }

    public MentorProfileListDto getRecommendProfileList(String userId) {
        Pageable topFour = PageRequest.of(0, 4);
        Mentee mentee = menteeRepository.findByUserId(userId);

        List<Profile> popularMentors = profileRepository.findAllByOrderByMentorChatCountDesc(topFour); // 가정
        List<Profile> wishAdmissionTypeMentors = profileRepository.findByAdmissionType(mentee.getWishAdmissionType(),topFour); // 가정
        List<Profile> wishCollegeMentors = profileRepository.findByCollege(mentee.getWishCollege(),topFour); // 가정

        return new MentorProfileListDto(popularMentors, wishAdmissionTypeMentors, wishCollegeMentors);

    }

}
