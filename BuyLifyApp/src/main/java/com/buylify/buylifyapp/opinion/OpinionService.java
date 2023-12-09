package com.buylify.buylifyapp.opinion;

import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.authentication.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OpinionService {

    private final OpinionRepository opinionRepository;
    private final UserRepository userRepository;

    public List<Opinion> getAllOpinions() {
        return opinionRepository.findAll();
    }

    public void addOpinion(CreateOpinionDto body, Long userId) {
        User sender = userRepository.findById(userId).orElseThrow();
        User receiver = userRepository.findById(body.getReceiverId()).orElseThrow();
        Optional<Opinion> opinionOptional = opinionRepository.findByUserSenderAndUserReceiver(userId, body.getReceiverId());
        Opinion opinion;

        if (opinionOptional.isPresent()) {
            opinion = opinionOptional.get();
        } else {
            opinion = new Opinion();
            opinion.setUserReceiver(receiver);
            opinion.setUserSender(sender);
        }

        opinion.setStars(body.getStars());
        opinion.setDescription(body.getDescription());

        opinionRepository.save(opinion);
    }
}
