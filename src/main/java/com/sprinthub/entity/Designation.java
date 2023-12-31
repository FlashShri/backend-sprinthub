package com.sprinthub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.sprinthub.entity.enums.DesignationTitle;

@Entity
@Table(name="designation")
public class Designation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int designationId;

    private DesignationTitle title;

    public int getDesignationId() {
        return designationId;
    }

    public void setDesignationId(int designationId) {
        this.designationId = designationId;
    }

    public DesignationTitle getTitle() {
        return title;
    }

    public void setTitle(DesignationTitle title) {
        this.title = title;
    }
}
