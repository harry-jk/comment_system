package kr.ac.jejunu.harry.repository;

import kr.ac.jejunu.harry.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jhkang on 2016-06-15.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByIdAndPassword(String id, String password);
}
