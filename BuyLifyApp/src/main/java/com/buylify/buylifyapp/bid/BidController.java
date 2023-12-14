package com.buylify.buylifyapp.bid;

import com.buylify.buylifyapp.security.SecurityUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bids")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    @GetMapping
    public List<Bid> getAllBids() {
        return bidService.getAllBids();
    }

    @PostMapping
    public void createBid(@RequestBody CreateBidDto createBidDto, Authentication authentication) {
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();
        bidService.createBid(createBidDto, userId);
    }

    @GetMapping("/winning/{idProduct}")
    public BidDto getWinningBid(@PathVariable Long idProduct) {
        return bidService.getWinningBid(idProduct);
    }
}
