package com.buylify.buylifyapp.opinion;

import com.buylify.buylifyapp.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opinions")
@RequiredArgsConstructor
public class OpinionController {

    private final OpinionService opinionService;

    @GetMapping
    public List<Opinion> getAllOpinions() {
        return opinionService.getAllOpinions();
    }

    @PostMapping
    public void addOpinion(@RequestBody CreateOpinionDto body,
                           Authentication authentication){
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();
        opinionService.addOpinion(body,userId);
    }

//    @GetMapping("/average/{userId}")
//    public float getAverageStars(@PathVariable Long userId){
//        return opinionService.getAverageStars(userId);
//    }

    @GetMapping("/{userId}")
    public List<OpinionDto> getOpinionsByUserId(@PathVariable Long userId){
        return opinionService.getOpinionsByUserId(userId);
    }
}
