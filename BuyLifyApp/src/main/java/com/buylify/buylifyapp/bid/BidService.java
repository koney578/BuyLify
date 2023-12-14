package com.buylify.buylifyapp.bid;

import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.authentication.UserRepository;
import com.buylify.buylifyapp.mappers.BidMapper;
import com.buylify.buylifyapp.product.Product;
import com.buylify.buylifyapp.product.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final BidMapper bidMapper;
    private final EntityManagerFactory entityManagerFactory;

    public List<Bid> getAllBids() {
        return bidRepository.findAll();
    }

    public void createBid(CreateBidDto createBidDto, Long userId) {
        Bid bid = new Bid();
        Product product = productRepository.findById(createBidDto.getIdProduct()).orElseThrow(() -> new RuntimeException("Not found"));

        if (product.getPrice() >= createBidDto.getPrice()) {
            throw new RuntimeException("Price is too low");
        }

        if (Objects.equals(product.getUser().getId(), userId)) {
            throw new RuntimeException("You can't bid your own product");
        }

        if (product.getAuctionEndsAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Auction has ended");
        }

        bid.setPrice(createBidDto.getPrice());
        bid.setUser(userRepository.getReferenceById(userId));
        bid.setProduct(productRepository.getReferenceById(createBidDto.getIdProduct()));
        bidRepository.save(bid);
    }

    public BidDto getWinningBid(Long idProduct) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Bid bid = entityManager.createQuery("SELECT b FROM Bid b WHERE b.product.id = :idProduct ORDER BY b.price DESC", Bid.class)
                .setParameter("idProduct", idProduct)
                .setMaxResults(1)
                .getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();


        return bidMapper.bidToBidDto(bid);
    }
}
