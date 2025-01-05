package com.kosa.jungdoin.member.controller;

import com.kosa.jungdoin.entity.TrainerApplication;
import com.kosa.jungdoin.member.dto.TrainerApplicationDTO;
import com.kosa.jungdoin.member.service.MemberService;
import com.kosa.jungdoin.admin.service.TrainerApplicationService;
import com.kosa.jungdoin.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final TrainerApplicationService trainerApplicationService;

    @GetMapping("/me")
    public ResponseEntity<Object> getContextUserInfo() {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            CustomUserDetails userDetails =
                    (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            resultMap.put("oauth", userDetails.getMemberOAuthId());
            resultMap.put("username", userDetails.getUsername());
            resultMap.put("role", userDetails.getRole());
            resultMap.put("id", userDetails.getId());

            return ResponseEntity.ok(resultMap);
        } catch (ClassCastException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletResponse response) {
        memberService.deleteAuthCookies(response);
        return ResponseEntity.ok("logout success");
    }

    @PostMapping("/trainer-application")
    public ResponseEntity<Object> applyTrainer(@RequestBody TrainerApplicationDTO requestDTO) {
        try {
            TrainerApplication trainerApplication = trainerApplicationService.saveTrainerApplication(requestDTO);
            return ResponseEntity.ok(trainerApplication);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
