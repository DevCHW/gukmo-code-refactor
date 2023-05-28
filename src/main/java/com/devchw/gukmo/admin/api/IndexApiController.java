package com.devchw.gukmo.admin.api;

import com.devchw.gukmo.admin.service.AdminMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class IndexApiController {
    private final AdminMemberService adminMemberService;


}
