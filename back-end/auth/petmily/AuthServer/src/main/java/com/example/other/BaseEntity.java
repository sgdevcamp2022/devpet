package com.example.other;//package com.example.shoh_oauth.data.entity;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@MappedSuperclass
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
////for @CreatedDate, @LastModifiedDate
//@EntityListeners(value = { AuditingEntityListener.class })
//public abstract class BaseEntity {
//
//    @Column(name="CREATE_AT", nullable = false, updatable = false)
//    @CreatedDate
//    private LocalDateTime createAt;
//
//    @Column(name="UPDATE_AT", nullable = false)
//    @LastModifiedDate
//    private LocalDateTime updateAt;
//}