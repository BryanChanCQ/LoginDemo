package com.cqns.demo.dao.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @Author BryanChan
 * @Date 2019-06-12 12:34
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@Entity
@Table(name = "t_branch_info", schema = "cqns", catalog = "")
public class BranchInfo {
    private String branCode;
    private String branName;
    private String supperBran;

    @Id
    @Column(name = "f_bran_code")
    public String getBranCode() {
        return branCode;
    }

    public void setBranCode(String branCode) {
        this.branCode = branCode;
    }

    @Basic
    @Column(name = "f_bran_name")
    public String getBranName() {
        return branName;
    }

    public void setBranName(String branName) {
        this.branName = branName;
    }

    @Basic
    @Column(name = "f_supper_bran")
    public String getSupperBran() {
        return supperBran;
    }

    public void setSupperBran(String supperBran) {
        this.supperBran = supperBran;
    }

}
