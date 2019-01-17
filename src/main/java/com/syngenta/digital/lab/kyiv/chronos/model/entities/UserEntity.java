package com.syngenta.digital.lab.kyiv.chronos.model.entities;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.Report;
import lombok.EqualsAndHashCode;
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
        name="userReportingQuery",
        query=" SELECT PROJECT.ID, PROJECT_NAME, sum(TASK.SPENT_TIME) as SPENT_TIME, USER.FIRST_NAME, USER.LAST_NAME " +
                "FROM TASK " +
                "       JOIN PROJECT ON TASK.PROJECT_ID = PROJECT.ID " +
                "       JOIN USER ON USER.ID = TASK.USER_ID " +
                "WHERE (TASK.REPORTING_DATE <=:endDate) and (TASK.REPORTING_DATE >=:startDate) AND USER.ID=:userId " +
                "GROUP BY PROJECT.ID, USER.ID ",
        resultSetMapping="reporting-dto-mapping"
)
@SqlResultSetMapping(name="reporting-dto-mapping",
        classes={
                @ConstructorResult(targetClass= Report.class, columns={
                        @ColumnResult(name="PROJECT_NAME", type=String.class),
                        @ColumnResult(name="SPENT_TIME", type=Long.class),
                        @ColumnResult(name="FIRST_NAME", type=String.class),
                        @ColumnResult(name="LAST_NAME", type=String.class)
                })
        }
)

@Entity
@Table(name = "USER")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "USER_EMAIL")
    private String userEmail;
    @Column(name = "USER_PASSWORD")
    private String userPassword;
}
