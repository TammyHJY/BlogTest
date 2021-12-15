package com.blog.demo.testTool;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderGenerator {

	public static void main(String[] args) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("john"));

	}

}

/* 在SQL SERVER 的users table 執行以下命令先建資料以利測試
 * INSERT INTO [dbo].users([email],[name],[password],[username]) VALUES ('tammyyang@gmail.com','tammyyang', '$2a$10$uiCvR3qPZ607asEH/Wc6Je9AmV7mV2PCuWvqClmLBQ.F0kLoGpjTa', 'tammyyang');
 * INSERT INTO [dbo].userRoles([userId],[roleId]) VALUES (2, 1);
 */
