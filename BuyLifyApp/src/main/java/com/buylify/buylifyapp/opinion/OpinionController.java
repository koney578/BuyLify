package com.buylify.buylifyapp.opinion;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
