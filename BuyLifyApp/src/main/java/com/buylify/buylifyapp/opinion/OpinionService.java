package com.buylify.buylifyapp.opinion;

import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.authentication.UserRepository;
import com.buylify.buylifyapp.mappers.OpinionMapper;
import com.buylify.buylifyapp.product.Product;
import com.buylify.buylifyapp.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OpinionService {

    private final OpinionRepository opinionRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OpinionMapper opinionMapper;

    public List<Opinion> getAllOpinions() {
        return opinionRepository.findAll();
    }

    public void addOpinion(CreateOpinionDto body, Long userId) {
        User sender = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        User receiver = userRepository.findById(body.getReceiverId()).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(body.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        Optional<Opinion> opinionOptional = opinionRepository.findByUserSenderAndUserReceiver(userId, body.getReceiverId(), body.getProductId());
        Opinion opinion;

        if (opinionOptional.isPresent()) {
            opinion = opinionOptional.get();
        } else {
            opinion = new Opinion();
            opinion.setUserReceiver(receiver);
            opinion.setUserSender(sender);
            opinion.setProduct(product);
        }

        opinion.setStars(body.getStars());
        opinion.setDescription(body.getDescription());

        opinionRepository.save(opinion);
    }
//
//    public float getAverageStars(Long userId) {
//        return opinionRepository.getAverageStars(userId);
//    }

    public List<OpinionDto> getOpinionsByUserId(Long userId) {
        return opinionRepository.findOpinionsByReceiverId(userId).stream()
                .map(opinion -> {
                    String username = userRepository.findById(opinion.getUserSender().getId()).orElseThrow().getUsername();
                    OpinionDto opinionDto = opinionMapper.toDto(opinion);
                    opinionDto.setUsername(username);
                    return opinionDto;
                })
                .toList();
    }

    public List<OpinionDto> getOpinionsByProductId(Long productId) {
        return opinionRepository.findOpinionsByProductId(productId).stream()
                .map(opinion -> {
                    String username = userRepository.findById(opinion.getUserSender().getId()).orElseThrow().getUsername();
                    OpinionDto opinionDto = opinionMapper.toDto(opinion);
                    opinionDto.setUsername(username);
                    return opinionDto;
                })
                .toList();
    }
}
