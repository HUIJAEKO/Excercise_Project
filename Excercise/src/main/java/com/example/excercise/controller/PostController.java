package com.example.excercise.controller;


import com.example.excercise.DTO.PageDTO;
import com.example.excercise.entity.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.excercise.DTO.CustomUserDetails;
import com.example.excercise.DTO.PostDTO;
import com.example.excercise.service.PostService;

import java.util.List;


@Controller
public class PostController {

	@Autowired
	private PostService postService;

	//새 글쓰기창 이동
	@GetMapping("/post/newPost")
	public String newPost() {
		return "post/newPost";
	}

	//새글쓰기
	@PostMapping("/post/newPost")
	public String postSave(@ModelAttribute PostDTO postDTO, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//authentication을 통해 정보 가져오기
		Object principal = authentication.getPrincipal();
		if (principal instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails) principal;
			model.addAttribute("username", userDetails.getName());
			postService.save(postDTO, userDetails.getId());
		}
		return "redirect:/user/main";
	}
//https://velog.io/@kk95610/Spring%EA%B2%8C%EC%8B%9C%ED%8C%90-%EB%A7%8C%EB%93%A4%EA%B8%B0
	//전체글보기
	@GetMapping("/post/allPost")
	public String allPost(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
		PageDTO pageDTO = new PageDTO();
		pageDTO.setPage((long) page);
		List<PostEntity> postList = postService.getList(pageDTO);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//authentication을 통해 정보 가져오기
		Object principal = authentication.getPrincipal();
		if (principal instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails) principal;
			model.addAttribute("username", userDetails.getName());
			model.addAttribute("postList", postList);
			model.addAttribute("pageDTO", pageDTO);
		}
		return "/post/allPost";
	}


}