package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.MentorJoinRequestDto;
import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.data.entity.Profile;
import com.example.chamedu_v1.data.repository.MentorRepository;
import com.example.chamedu_v1.data.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MentorAccessServiceImpl implements MentorAccessService {

    private final MentorRepository mentorRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public MentorAccessServiceImpl(MentorRepository mentorRepository, ProfileRepository profileRepository) {
        this.mentorRepository = mentorRepository;
        this.profileRepository= profileRepository;
    }

    @Override
    @Transactional
    public void registerMentor(MentorJoinRequestDto mentorJoinRequestDto) {
        Mentor mentor = new Mentor();
        mentor.setUserId(mentorJoinRequestDto.getUserId());
        mentor.setPassword(mentorJoinRequestDto.getPassword());
        mentor.setNickname(mentorJoinRequestDto.getNickname());
        mentor.setName(mentorJoinRequestDto.getName());
        mentor.setUserImg(mentorJoinRequestDto.getUserImg());
        mentorRepository.save(mentor); // 멘토 엔티티 저장

        Profile profile= new Profile();
        profile.setProfileImg(mentorJoinRequestDto.getUserImg());
        profile.setUniversity(mentorJoinRequestDto.getUniversity());
        profile.setMentor(mentor); // 멘토와의 관계반영
        profileRepository.save(profile); // 프로필 엔티티 저장
    }

    @Override
    public boolean authenticateUser(Mentor mentor) {
        Mentor storedMentor = mentorRepository.findByUserId(mentor.getUserId());
        return storedMentor != null && storedMentor.getPassword().equals(mentor.getPassword());
    }
}