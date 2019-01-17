package com.syngenta.digital.lab.kyiv.chronos.model.entities;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.TagDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

@NamedNativeQuery(
        name="findAllTagsForTaskId",
        query=" SELECT * FROM TAG " +
                "RIGHT JOIN TASK_TAGS on TAG.ID = TASK_TAGS.TAG_ID " +
                "RIGHT JOIN TASK on TASK_TAGS.TASK_ID = TASK.ID " +
                "WHERE TASK.ID=:taskId",
        resultSetMapping="tag-dto-mapping"
)
@SqlResultSetMapping(name="tag-dto-mapping",
        classes={
                @ConstructorResult(targetClass= TagDto.class, columns={
                        @ColumnResult(name="ID", type=Long.class),
                        @ColumnResult(name="TAG", type=String.class)
                })
        }
)
@Entity
@Table(name = "TAG")
@Getter
@Setter
@ToString
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TAG")
    private String tag;
}
