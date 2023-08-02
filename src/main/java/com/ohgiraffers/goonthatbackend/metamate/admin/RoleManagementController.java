package com.ohgiraffers.goonthatbackend.metamate.admin;

import com.ohgiraffers.goonthatbackend.metamate.auth.LoginUser;
import com.ohgiraffers.goonthatbackend.metamate.web.dto.user.SessionMetaUser;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class RoleManagementController {

    private final RoleManagementService roleManagementService;

    @GetMapping("/admin/members")
    public String members() {
        return "admin/members";
    }

    @ResponseBody
    @GetMapping("/api/admin/members")
    public List<RoleManagementDto> getMembers() {
        return roleManagementService.users();
    }

    @ResponseBody
    @PostMapping("/api/admin/members")
    public List<RoleManagementDto> update(@ModelAttribute RoleManagementDto roleManagementDto) {
        roleManagementService.update(roleManagementDto);
        return roleManagementService.users(); // 업데이트 후 변경된 리스트를 바로 반환
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    @DeleteMapping("/api/admin/members")
    public void delete(@RequestBody RoleManagementDto roleManagementDto) {
        roleManagementService.deleteUser(roleManagementDto.getEmail());
    }
}
