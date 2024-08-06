package vn.bt.spring.qlsv_be.entity;

import jakarta.persistence.*;

import java.sql.Blob;
import java.sql.Date;

@Entity
@Table(name = "student_detail")
public class StudentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "gender")
    private boolean gender;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "phone_num", length = 10)
    private String phoneNum;
    @Column(name = "address", length = 256)
    private String address;
    @Column(name = "facebook", length = 256)
    private String facebook;
    @Lob
    @Column(name = "avatar")
    private Blob avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public Blob getAvatar() {
        return avatar;
    }

    public void setAvatar(Blob avatar) {
        this.avatar = avatar;
    }

    public StudentDetail() {
    }

    public StudentDetail(boolean gender, Date dateOfBirth, String phoneNum, String address, String facebook, Blob avatar) {
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNum = phoneNum;
        this.address = address;
        this.facebook = facebook;
        this.avatar = avatar;
    }
}
