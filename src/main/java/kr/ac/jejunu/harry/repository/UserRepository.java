package kr.ac.jejunu.harry.repository;

import kr.ac.jejunu.harry.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by jhkang on 2016-06-15.
 */
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    User findByIdAndPassword(String id, String password);
}
