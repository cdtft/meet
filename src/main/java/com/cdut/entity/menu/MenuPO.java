package com.cdut.entity.menu;

import com.cdut.enums.CommonStatusEnum;
import com.cdut.vo.MenuOneLevelRespVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单栏
 * Created by king on 2017/10/25.
 */
@Entity
@Table(name = "menu_side_bar")
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "oneMenuLevel",
                classes = {
                        @ConstructorResult(
                                targetClass = MenuOneLevelRespVO.class,
                                columns = {
                                        @ColumnResult(name = "id"),
                                        @ColumnResult(name = "name"),
                                        @ColumnResult(name = "indexNum")
                                }
                        )
                }
        )
})
public class MenuPO implements Serializable {

    private static final long serialVersionUID = -8303679244417334593L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 侧边栏名称
     */
    @Column(unique = true, length = 40)
    private String name;

    /**
     * 显示排序
     */
    private Integer indexNum;

    /**
     * 指向文章的ID
     */
    private Long articleId;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private List<MenuPO> childMenu;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(Integer indexNum) {
        this.indexNum = indexNum;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public List<MenuPO> getChildMenu() {
        return childMenu;
    }

    public void setChildMenu(List<MenuPO> childMenu) {
        this.childMenu = childMenu;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("indexNum", indexNum)
                .append("articleId", articleId)
                .append("childMenu", childMenu)
                .toString();
    }
}
