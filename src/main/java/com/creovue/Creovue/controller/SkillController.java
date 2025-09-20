package com.creovue.Creovue.controller;

import com.creovue.Creovue.dto.SkillRequest;
import com.creovue.Creovue.dto.SkillResponse;
import com.creovue.Creovue.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping("/{userId}")
    public ResponseEntity<SkillResponse> addSkill(
            @PathVariable Long userId,
            @Valid @RequestBody SkillRequest request) {
        return ResponseEntity.ok(skillService.addSkill(userId, request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SkillResponse>> getUserSkills(@PathVariable Long userId) {
        return ResponseEntity.ok(skillService.getUserSkills(userId));
    }
}
