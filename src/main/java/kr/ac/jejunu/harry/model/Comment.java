package kr.ac.jejunu.harry.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by jhkang on 2016-06-12.
 */
@Entity
@Table(name = "comments")
public class Comment {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
    @Column(name="created_at")
    @JsonIgnore
    private Date createdAt = new Date();

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

    public Date getCreatedAt() {
        return createdAt;
    }

    @JsonGetter("created_at")
    public String getCreateAtToString() {
        return dateFormat.format(createdAt);
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setDislike(Set<Opinion> dislike) {
        this.dislike = dislike;
    }

    public void setLike(Set<Opinion> like) {
        this.like = like;
    }

    @JsonGetter("like")
    public int getLikeCount() {
        if(like == null) {
            return 0;
        } else {
            return like.size();
        }
    }

    @JsonGetter("dislike")
    public int getDislikeCount() {
        if(dislike == null) {
            return 0;
        } else {
            return dislike.size();
        }
    }
}
