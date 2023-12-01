package com.buylify.buylifyapp.bid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;

    public List<Bid> getAllBids() {
        return bidRepository.findAll();
    }
}
