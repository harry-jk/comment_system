package kr.ac.jejunu.harry.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by jhkang on 2016-06-16.
 */
@Entity
@Table(name = "comment_opinions")
public class Opinion {
    public enum TYPE {
        LIKE, DISLIKE
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    @JoinColumn(name = "cid")
    @ManyToOne
    private Comment comment;
    @JoinColumn(name = "uid")
    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private TYPE type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }
}
