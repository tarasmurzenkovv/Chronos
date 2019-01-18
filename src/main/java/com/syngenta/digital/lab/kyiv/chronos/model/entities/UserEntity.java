package com.syngenta.digital.lab.kyiv.chronos.model.entities;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.Report;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import java.time.LocalDate;

@NamedNativeQuery(
        name = "userReportingQuery",
        query = "SELECT " +
                "       TASK.ID AS TASK_ID,   " +
                "       PROJECT_NAME, " +
                "       FIRST_NAME, " +
                "       LAST_NAME, " +
                "       JOB_TITLE, " +
                "       SPENT_TIME, " +
                "       REPORTING_DATE, " +
                "       COMMENTS " +
                "FROM TASK " +
                "       JOIN PROJECT ON PROJECT.ID = PROJECT_ID " +
                "       JOIN USER ON USER.ID = USER_ID " +
                "WHERE REPORTING_DATE <=:endDate AND REPORTING_DATE >=:startDate AND USER.ID in :userIds " +
                "GROUP BY PROJECT_ID, PROJECT_NAME, FIRST_NAME, LAST_NAME, JOB_TITLE, TASK_ID, SPENT_TIME, REPORTING_DATE, COMMENTS " +
                "ORDER BY FIRST_NAME, LAST_NAME",
        resultSetMapping = "reporting-dto-mapping"
)
@SqlResultSetMapping(name = "reporting-dto-mapping",
        classes = {
                @ConstructorResult(targetClass = Report.class, columns = {
                        @ColumnResult(name = "TASK_ID", type = Long.class),
                        @ColumnResult(name = "PROJECT_NAME", type = String.class),
                        @ColumnResult(name = "FIRST_NAME", type = String.class),
                        @ColumnResult(name = "LAST_NAME", type = String.class),
                        @ColumnResult(name = "JOB_TITLE", type = String.class),
                        @ColumnResult(name = "SPENT_TIME", type = Long.class),
                        @ColumnResult(name = "REPORTING_DATE", type = LocalDate.class),
                        @ColumnResult(name = "COMMENTS", type = String.class)
                })
        }
)

@Entity
@Table(name = "USER")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private UserRoleEntity userRoleEntity;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "USER_EMAIL")
    private String userEmail;
    @Column(name = "USER_PASSWORD")
    private String userPassword;
    @Column(name = "JOB_TITLE")
    private String jobTitle;
}
