package com.buylify.buylifyapp.opinion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpinionService {

    private final OpinionRepository opinionRepository;

    public List<Opinion> getAllOpinions() {
        return opinionRepository.findAll();
    }
}
