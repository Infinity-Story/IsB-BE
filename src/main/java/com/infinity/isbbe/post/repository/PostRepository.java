package com.infinity.isbbe.post.repository;

import com.infinity.isbbe.post.aggregate.Post;
import com.infinity.isbbe.post.etc.POST_CATEGORY;
import com.infinity.isbbe.post.etc.POST_STATUS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByPostCode(int postCode);

    List<Post> findAllByMemberMemberCode(int memberCode);

    List<Post> findAllByPostStatus(POST_STATUS postStatus);

    List<Post> findAllByPostCategory(POST_CATEGORY postCategory);
}
