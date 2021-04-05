package com.example.generaltask.model;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import java.util.Date;


/**
 * Class to provide auditing attributes and columns for tables
 * for entities extending this class.
 * possibilities for auditing fields.
 * <ul>
 *  <li>add @CreatedBy</li>
 *  <li>add @LastModifiedBy</li>
 *  <li>add @LastModifiedDate</li>
 * </ul>
 * For 'By' fields, make this class generic and make by fields generic.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class DateAuditable  {

    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;

    public Date getCreatedDate() {
        return createdDate;
    }

    protected void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
