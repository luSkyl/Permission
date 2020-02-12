package com.java.lcy.Permission.Param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

    private String subject;//邮件主题

    private String message;//邮件信息

    private Set<String> receivers;//收件人邮箱
}
