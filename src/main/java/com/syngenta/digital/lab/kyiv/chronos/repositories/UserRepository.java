package com.syngenta.digital.lab.kyiv.chronos.repositories;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.Range;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.Report;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select count(user.userEmail) from UserEntity user where user.userEmail=:email")
    Long countEmails(@Param("email") String email);

    @Query("select user from UserEntity user where user.userEmail=:email")
    Optional<UserEntity> find(@Param("email") String email);

    default  Stream<Report> generateReport(Long userId, Range range) {
        return this.generateReport(userId, range.getStart(), range.getEnd());
    }

    @Query(nativeQuery = true, name = "userReportingQuery")
    Stream<Report> generateReport(@Param("userId") Long userId,
                                  @Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate);
}
