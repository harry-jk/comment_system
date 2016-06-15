package kr.ac.jejunu.harry.repository;

import kr.ac.jejunu.harry.model.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by jhkang on 2016-06-15.
 */
public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer> {
}
