package com.example.excercise.service;

import java.util.List;
import java.util.Optional;

import com.example.excercise.DTO.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.excercise.DTO.PostDTO;
import com.example.excercise.entity.PostEntity;
import com.example.excercise.entity.UserEntity;
import com.example.excercise.repository.PostRepository;
import com.example.excercise.repository.UserRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;
	
	public void save(PostDTO postDTO, Long userId) {
		PostEntity postEntity = PostDTO.toPostEntity(postDTO); 
		UserEntity userEntity = userRepository.findById(userId).orElse(null);
		postEntity.setUserEntity(userEntity);
		postRepository.save(postEntity);
	}
	
	public List<PostEntity> find5Post(){
		return postRepository.findAllByOrderByPostCreatedTimeDesc(PageRequest.of(0, 5));
	}

	public List<PostEntity> getList(PageDTO pageDTO) {
		Long totalCount = postRepository.count();
		pageDTO.setRow();
		pageDTO.setNum(totalCount);

		Pageable pageable = PageRequest.of(pageDTO.getPage().intValue() - 1, pageDTO.getPostPerPage().intValue());
		return postRepository.findAll(pageable).getContent();
	}
}
