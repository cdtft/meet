package com.cdut.entity.common;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 文章的基础类
 * Created by king on 2017/10/25.
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class BaseArticleEntity extends BasePo<Long> implements Serializable {

    private static final long serialVersionUID = 6241774389468131647L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp createTimestamp = new Timestamp(System.currentTimeMillis());

    @UpdateTimestamp
    private Timestamp updateTimestamp;

    /**
     * 文章类容
     */
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;

    /**
     * 作者
     */
    @Column(length = 40)
    private String author;

    @Column(length = 40)
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public Timestamp getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Timestamp updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "#" + this.getId();
    }
}