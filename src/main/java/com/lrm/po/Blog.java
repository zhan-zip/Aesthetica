package com.lrm.po;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "t_blog")
public class Blog {

    @Id
    @GeneratedValue
    private Long id;        //嗯
    private String title;       //标题
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;     //内容
    private String firstPicture;        //首图
    private String flag;        //标记
    private Integer view;       //浏览次数
    private boolean appreciation;       //赞赏开启
    private boolean shareStatement;      //转载声明
    private boolean commentabled;       //评论
    private boolean published;      //发布
    private boolean recommend;     //推荐
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;        //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;        //更新时间


    @ManyToOne      //关系维护端
    private Type type;      //跟type的关系

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();


    public Blog(){

    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", view=" + view +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
