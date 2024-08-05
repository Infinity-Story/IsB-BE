package com.infinity.isbbe.post.repository;

import com.infinity.isbbe.post.aggregate.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByPostCode(int postCode);

    List<Post> findAllByMemberMemberCode(int memberCode);
}
