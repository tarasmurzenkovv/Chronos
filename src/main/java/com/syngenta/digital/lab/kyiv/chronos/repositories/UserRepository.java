package com.syngenta.digital.lab.kyiv.chronos.repositories;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.Range;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.Report;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.ReportingRequest;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("select count(user.userEmail) from UserEntity user where user.userEmail=:email")
    long countEmails(@Param("email") String email);

    @Query("select user from UserEntity user join fetch user.userRoleEntity where user.userEmail=:email")
    Optional<UserEntity> find(@Param("email") String email);

    default List<Report> generateReport(ReportingRequest reportingRequest) {
        Range range = reportingRequest.getRange();
        return this.generateReport(reportingRequest.getUserIds(), range.getStart(), range.getEnd());
    }

    @Query(nativeQuery = true, name = "userReportingQuery")
    List<Report> generateReport(@Param("userIds") List<Long> userIds,
                                @Param("startDate") LocalDate startDate,
                                @Param("endDate") LocalDate endDate);

    @Modifying
    @Query("update UserEntity user set user.userPassword=:password where user.userEmail=:email")
    void updatePassword(@Param("password") String newPassword, @Param("email") String email);
}
