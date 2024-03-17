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
	
	//글작성
	public void save(PostDTO postDTO, Long userId) {
		PostEntity postEntity = PostDTO.toPostEntity(postDTO); 
		UserEntity userEntity = userRepository.findById(userId).orElse(null);
		postEntity.setUserEntity(userEntity);
		postRepository.save(postEntity);
	}
	
	//최신글 8개 불러오기
	public List<PostEntity> find8Post(){
		return postRepository.findAllByOrderByPostCreatedTimeDesc(PageRequest.of(0, 8));
	}

	//페이징처리 DTO설정
	public List<PostEntity> getList(PageDTO pageDTO) {
		Long totalCount = postRepository.count();
		pageDTO.setRow();
		pageDTO.setNum(totalCount);
		Pageable pageable = PageRequest.of(pageDTO.getPage().intValue() - 1, pageDTO.getPostPerPage().intValue());
		return postRepository.findAll(pageable).getContent();
	}

	//글작성자 불러오기
	public UserEntity getPostWriter(Long id){
		Optional<PostEntity> postEntityOptional = postRepository.findById(id);
		if (postEntityOptional.isPresent()) {
			PostEntity postEntity = postEntityOptional.get();
			return postEntity.getUserEntity();
		} else {
			return null; // 혹은 적절한 예외 처리
		}
	}

	//글 상세조회
	public PostDTO postDetail(Long id) {
		Optional<PostEntity> post = postRepository.findById(id);
		if(post.isPresent()){
			PostEntity postEntity = post.get();
			PostDTO postDTO = PostDTO.toPostDTO(postEntity);
			return postDTO;
		}else{
			return null;
		}
	}

	//글삭제
	public void deletePost(Long id) {
		postRepository.deleteById(id);
	}
}
