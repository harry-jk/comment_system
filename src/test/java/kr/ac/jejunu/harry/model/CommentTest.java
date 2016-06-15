package kr.ac.jejunu.harry.model;

import kr.ac.jejunu.harry.hibernate.BasicHibernateTest;
import org.hibernate.Session;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by jhkang on 2016-06-13.
 */
public class CommentTest extends BasicHibernateTest {
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private User user;

    @Override
    public void setup() {
        super.setup();
        user = getTestUser();
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void finish() {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
        super.finish();
    }

    @Test
    public void add() {
        Session session = sessionFactory.openSession();
        Comment comment = getTestComment();

        session.getTransaction().begin();
        session.save(comment);
        session.getTransaction().commit();

        Comment savedComment = (Comment) session.get(Comment.class, comment.getCid());

        assertThat(savedComment.getUser().getUid(), is(user.getUid()));
        assertThat(savedComment.getComment(), is(comment.getComment()));
        assertThat(savedComment.getCreated_at(), is(comment.getCreated_at()));

        session.close();
    }

    @Test
    public void get() {
        Session session = sessionFactory.openSession();
        Comment comment = (Comment) session.get(Comment.class, 1);

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(2016, 5, 15, 15, 42, 29);
        Date date = calendar.getTime();

        assertThat(comment.getUser().getUid(), is(1));
        assertThat(comment.getComment(), is("i'm Harry!"));
        assertThat(dateFormat.format(comment.getCreated_at()),
                is(dateFormat.format(date)));

        session.close();
    }

    @Test
    public void edit() {
        Session session = sessionFactory.openSession();
        Comment comment = getTestComment();

        session.getTransaction().begin();
        session.save(comment);
        session.getTransaction().commit();

        Comment savedComment = (Comment) session.get(Comment.class, comment.getCid());

        String commentStr = "Test Edit!";
        Date editDate = new Date();

        savedComment.setComment(commentStr);
        savedComment.setCreated_at(editDate);

        session.getTransaction().begin();
        session.save(savedComment);
        session.getTransaction().commit();

        Comment editedComment = (Comment) session.get(Comment.class, comment.getCid());

        assertThat(editedComment.getUser().getUid(), is(comment.getUser().getUid()));
        assertThat(editedComment.getComment(), is(commentStr));
        assertThat(dateFormat.format(editedComment.getCreated_at()),
                is(dateFormat.format(editDate)));

        session.close();
    }

    @Test
    public void delete() {
        Session session = sessionFactory.openSession();
        Comment comment = getTestComment();

        session.getTransaction().begin();
        session.save(comment);
        session.getTransaction().commit();

        Comment savedComment = (Comment) session.get(Comment.class, comment.getCid());

        session.getTransaction().begin();
        session.delete(savedComment);
        session.getTransaction().commit();

        Comment deletedComment = (Comment) session.get(Comment.class, comment.getCid());
        assertNull(deletedComment);

        session.close();
    }

    private Comment getTestComment() {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setComment("Test");
        comment.setCreated_at(new Date());
        return comment;
    }
}
