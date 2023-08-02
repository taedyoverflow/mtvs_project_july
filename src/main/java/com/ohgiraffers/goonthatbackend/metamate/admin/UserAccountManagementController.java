package com.ohgiraffers.goonthatbackend.metamate.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class UserAccountManagementController {

    private final UserAccountManagementService userAccountManagementService;

    @GetMapping("/management/user-accounts")
    public String getUserAccounts(Model model) {
        model.addAttribute("userAccounts", userAccountManagementService.getUserAccounts());

        return "management/user-accounts";
    }

    @ResponseBody
    @GetMapping("/management/user-accounts/{id}")
    public Optional<UserAccountManagementDto> userAccount(@PathVariable Long id) {
        return userAccountManagementService.getUserAccount(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    @DeleteMapping("/management/user-accounts/{id}")
    public String deleteUserAccount(@PathVariable Long id) {
        userAccountManagementService.deleteUserAccount(id);

        return "redirect:/management/user-accounts";
    }

}