package kr.ac.jejunu.harry.repository;

import kr.ac.jejunu.harry.model.Comment;
import kr.ac.jejunu.harry.model.Opinion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by jhkang on 2016-06-16.
 */
public interface OpinionRepository extends CrudRepository<Opinion, Integer> {
    List<Opinion> findAllByComment(Comment comment);
}
