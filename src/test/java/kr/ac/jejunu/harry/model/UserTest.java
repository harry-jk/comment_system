package kr.ac.jejunu.harry.model;

import kr.ac.jejunu.harry.hibernate.BasicHibernateTest;
import org.hibernate.Session;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;

/**
 * Created by jhkang on 2016-06-13.
 */
public class UserTest extends BasicHibernateTest{

    @Test
    public void add() {
        Session session = sessionFactory.openSession();
        User user = getTestUser();

        session.getTransaction().begin();
        session.save(user);
        session.getTransaction().commit();

        User savedUser = (User) session.get(User.class, user.getUid());

        assertThat(savedUser.getId(), is(user.getId()));
        assertThat(savedUser.getPassword(), is(user.getPassword()));
        assertThat(savedUser.getName(), is(user.getName()));
        assertThat(savedUser.getDescription(), is(user.getDescription()));
        assertThat(savedUser.getProfile_image_url(), is(user.getProfile_image_url()));

        session.close();
        deleteTestObject(user);
    }

    @Test
    public void get() {
        Session session = sessionFactory.openSession();
        User user = (User) session.get(User.class, 1);

        assertThat(user.getId(), is("harry.jk"));
        assertThat(user.getPassword(), is("1234"));
        assertThat(user.getName(), is("Harry"));
        assertThat(user.getDescription(), is("Software Engineer"));
        assertThat(user.getProfile_image_url(), is("/resources/harry.jpg"));

        session.close();
    }

    @Test
    public void edit() {
        Session session = sessionFactory.openSession();
        User user = getTestUser();

        session.getTransaction().begin();
        session.save(user);
        session.getTransaction().commit();

        User savedUser = (User) session.get(User.class, user.getUid());
        String name = "Test";
        String desc = "Test Desc";
        savedUser.setName(name);
        savedUser.setDescription(desc);

        session.getTransaction().begin();
        session.save(savedUser);
        session.getTransaction().commit();

        User editedUser = (User) session.get(User.class, user.getUid());

        assertThat(editedUser.getId(), is(user.getId()));
        assertThat(editedUser.getPassword(), is(user.getPassword()));
        assertThat(editedUser.getName(), is(name));
        assertThat(editedUser.getDescription(), is(desc));
        assertThat(editedUser.getProfile_image_url(), is(user.getProfile_image_url()));

        session.close();
        deleteTestObject(user);
    }

    @Test
    public void delete() {
        Session session = sessionFactory.openSession();
        User user = getTestUser();

        session.getTransaction().begin();
        session.save(user);
        session.getTransaction().commit();

        User savedUser = (User) session.get(User.class, user.getUid());

        session.getTransaction().begin();
        session.delete(savedUser);
        session.getTransaction().commit();

        User deletedUser = (User) session.get(User.class, savedUser.getUid());
        assertNull(deletedUser);

        session.close();
    }
}
