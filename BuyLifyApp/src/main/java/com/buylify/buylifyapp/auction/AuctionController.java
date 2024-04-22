package com.buylify.buylifyapp.auction;

import com.buylify.buylifyapp.firebase.FirebaseFileService;
import com.buylify.buylifyapp.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/auctions")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;
    private final FirebaseFileService firebaseFileService;

    @PostMapping
    public void addAuction(@RequestBody CreateAuctionDto post,
                           @RequestPart("file") MultipartFile file,
                           Authentication authentication) throws IOException {
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();
        String fileName = firebaseFileService.saveTest(file);
        auctionService.addAction(post, fileName, userId);
    }

    @GetMapping
    public List<AuctionDto> getAllAuctions() {
        return auctionService.getAllAuctions();
    }

    @PatchMapping("/{id}")
    public void placeBid(@PathVariable Long id, @RequestBody bidDto body, Authentication authentication) {
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();
        auctionService.placeBid(id, body, userId);
    }

}
