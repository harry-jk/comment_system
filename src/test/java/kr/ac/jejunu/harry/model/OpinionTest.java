package kr.ac.jejunu.harry.model;

import kr.ac.jejunu.harry.hibernate.BasicHibernateTest;
import org.hibernate.Session;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by jhkang on 2016-06-16.
 */
public class OpinionTest extends BasicHibernateTest {

    @Test
    public void get() {
        Session session = sessionFactory.openSession();
        Opinion opinion = (Opinion) session.get(Opinion.class, 1);

        assertThat(opinion.getUser().getUid(), is(1));
        assertThat(opinion.getComment().getCid(), is(1));
        assertThat(opinion.getType(), is(Opinion.TYPE.LIKE));
        session.close();
    }

    @Test
    public void add() {
        User user = getTestUser();
        Comment comment = getTestComment(user);

        Session session = sessionFactory.openSession();
        Opinion opinion = getTestOpinion(user, comment, Opinion.TYPE.DISLIKE);

        session.getTransaction().begin();
        session.save(opinion);
        session.getTransaction().commit();

        Opinion savedOpinion = (Opinion) session.get(Opinion.class, opinion.getId());

        assertThat(savedOpinion.getComment().getCid(), is(opinion.getComment().getCid()));
        assertThat(savedOpinion.getUser().getUid(), is(opinion.getUser().getUid()));
        assertThat(savedOpinion.getType(), is(Opinion.TYPE.DISLIKE));

        session.close();
        deleteTestObject(opinion);
        deleteTestObject(comment);
        deleteTestObject(user);
    }

    @Test
    public void delete() {
        User user = getTestUser();
        Comment comment = getTestComment(user);

        Session session = sessionFactory.openSession();
        Opinion opinion = getTestOpinion(user, comment, Opinion.TYPE.LIKE);

        session.getTransaction().begin();
        session.save(opinion);
        session.getTransaction().commit();

        Opinion savedOpinion = (Opinion) session.get(Opinion.class, opinion.getId());

        session.getTransaction().begin();
        session.delete(savedOpinion);
        session.getTransaction().commit();

        Opinion deletedOpinion = (Opinion) session.get(Opinion.class, opinion.getId());

        assertNull(deletedOpinion);

        session.close();
        deleteTestObject(comment);
        deleteTestObject(user);
    }

    @Override
    protected User getTestUser() {
        User user = super.getTestUser();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();
        session.save(user);
        session.getTransaction().commit();

        session.close();
        return user;
    }


    @Override
    protected Comment getTestComment(User user) {
        Comment comment = super.getTestComment(user);
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();
        session.save(comment);
        session.getTransaction().commit();

        session.close();
        return comment;
    }

    private Opinion getTestOpinion(User user, Comment comment, Opinion.TYPE type) {
        Opinion opinion = new Opinion();
        opinion.setUser(user);
        opinion.setComment(comment);
        opinion.setType(type);
        return opinion;
    }
}
