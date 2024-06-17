package com.buylify.buylifyapp.auction;

import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.authentication.UserRepository;
import com.buylify.buylifyapp.category.Category;
import com.buylify.buylifyapp.category.CategoryRepository;
import com.buylify.buylifyapp.mappers.AuctionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctionService {

    public static final String IMAGE_PREFIX = "https://firebasestorage.googleapis.com/v0/b/projekt-zespolowy-5362c.appspot.com/o/";
    public static final String MEDIA_PARAM = "?alt=media&token=";

    private final AuctionRepository auctionRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private final AuctionMapper mapper;

    public void addAction(CreateAuctionDto post, String fileName, Long userId) {
        Auction auction = mapper.toEntity(post);
        String imageUrl = IMAGE_PREFIX + fileName + MEDIA_PARAM + fileName;

        Category category = categoryRepository.getReferenceById(post.getCategoryId());
        User owner = userRepository.getReferenceById(userId);

        auction.setPhoto(imageUrl);
        auction.setCategory(category);
        auction.setCurrentPrice(auction.getStartPrice());
        auction.setActive(true);
        auction.setOwner(owner);

        auctionRepository.save(auction);
    }

    public Optional<AuctionDto> getAuction(Long id) {
        return auctionRepository.findById(id)
                .map(mapper::toAuctionDto);
    }

    public List<AuctionDto> getAllAuctions() {
        return auctionRepository.findAll()
                .stream()
                .map(mapper::toAuctionDto)
                .toList();
    }

    public void placeBid(Long id, bidDto body, Long userId) {
        Auction auction = auctionRepository.findById(id).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        if (auction.getEndDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Auction has ended");
        }

        if (auction.getCurrentPrice() >= body.getCurrentPrice()) {
            throw new RuntimeException("Bid is too low");
        }

        auction.setCurrentPrice(body.getCurrentPrice());
        auction.setWinner(user);
        auction.setLastBidDate(LocalDateTime.now());

        auctionRepository.save(auction);
    }

    public List<AuctionDto> getUserWinningAuctions(Long userId) {

        return auctionRepository.findAllByWinnerId(userId)
                .stream()
                .map(mapper::toAuctionDto)
                .toList();
    }
}
