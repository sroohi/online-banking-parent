package com.rab3tech.admin.controller;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rab3tech.vo.RoleVO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v3")
public class RoleController {
	
	@GetMapping("/roles/{rid}")
	public RoleVO getRoleVO(@PathVariable int rid) {
		RoleVO roleVO=new RoleVO();
		roleVO.setId(rid);	
		roleVO.setName("ADMIN_ROLE");
		roleVO.setDescription("This super power role!");
		roleVO.setDoe(new Timestamp(new Date().getTime()));
		roleVO.setDom(new Timestamp(new Date().getTime()));
		return roleVO;
	}
}
