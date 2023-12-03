package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.entity.Mentee;
import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.data.repository.MenteeRepository;
import com.example.chamedu_v1.data.dto.PointChangeRequestDto;
import com.example.chamedu_v1.data.repository.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PointManageServiceImpl implements PointManageService{

    private final MenteeRepository menteeRepository;
    private final MentorRepository mentorRepository;

    @Autowired
    public PointManageServiceImpl(MenteeRepository menteeRepository, MentorRepository mentorRepository) {
        this.menteeRepository = menteeRepository;
        this.mentorRepository = mentorRepository;
    }

    @Override
    public void purchasePoints(PointChangeRequestDto purchaseDTO) {
        String userId = purchaseDTO.getUserId();
        int purchasedPoints = purchaseDTO.getChangedPoints();

        Mentee mentee = menteeRepository.findByUserId(userId);
        mentee.setPoint(mentee.getPoint() + purchasedPoints);
        menteeRepository.save(mentee);
    }

    @Override
    public void refundPoints(PointChangeRequestDto refundDTO) {
        String userId = refundDTO.getUserId();
        int refundedPoints = refundDTO.getChangedPoints();

        Mentor mentor = mentorRepository.findByUserId(userId);
        mentor.setPoint(mentor.getPoint()    - refundedPoints);
        mentorRepository.save(mentor);

    }

}
