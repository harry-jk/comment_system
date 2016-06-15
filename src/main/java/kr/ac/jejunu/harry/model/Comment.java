package kr.ac.jejunu.harry.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by jhkang on 2016-06-12.
 */
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;

    @JoinColumn(name = "uid")
    @ManyToOne
    private User user;
    private String comment;

    @JoinColumn(name = "cid")
    @OneToMany
    @Where(clause = "type = 'LIKE'")
    private Set<Opinion> like;

    @JoinColumn(name = "cid")
    @OneToMany
    @Where(clause = "type = 'DISLIKE'")
    private Set<Opinion> dislike;

    @CreatedDate
    private Date created_at = new Date();

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setDislike(Set<Opinion> dislike) {
        this.dislike = dislike;
    }

    public void setLike(Set<Opinion> like) {
        this.like = like;
    }

    @JsonGetter("like")
    public int getLikeCount() {
        return like.size();
    }

    @JsonGetter("dislike")
    public int getDislikeCount() {
        return dislike.size();
    }
}
