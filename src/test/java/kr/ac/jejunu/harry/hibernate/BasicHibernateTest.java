package kr.ac.jejunu.harry.hibernate;

import kr.ac.jejunu.harry.model.Comment;
import kr.ac.jejunu.harry.model.Opinion;
import kr.ac.jejunu.harry.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;

import java.util.Date;

/**
 * Created by jhkang on 2016-06-13.
 */
public class BasicHibernateTest {
    protected SessionFactory sessionFactory;

    @Before
    public void setup() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Comment.class)
                .addAnnotatedClass(Opinion.class);

        StandardServiceRegistryBuilder sb = new StandardServiceRegistryBuilder();
        sb.applySettings(configuration.getProperties());

        StandardServiceRegistry standardServiceRegistry = sb.build();
        sessionFactory = configuration.buildSessionFactory(standardServiceRegistry);
    }

    @After
    public void finish() {
        sessionFactory.close();
    }

    protected User getTestUser() {
        User user = new User();
        user.setId("test");
        user.setPassword("1234");
        user.setName("Harry");
        user.setDescription("Software Engineer");
        user.setProfile_image_url("/resources/test.jpg");
        return user;
    }

    protected Comment getTestComment(User user) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setComment("Test");
        comment.setCreatedAt(new Date());
        return comment;
    }

    protected void deleteTestObject(Object object) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.delete(object);
        session.getTransaction().commit();
        session.close();
    }
}
