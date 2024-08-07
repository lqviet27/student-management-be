package vn.bt.spring.qlsv_be.model.dto;

import java.sql.Date;

public class StudentDetailDTO {
    private int id;
    private boolean gender;
    private Date dateOfBirth;
    private String phoneNum;
    private String address;
    private String facebook;
    private byte[] avatar; // Represent avatar as a byte array

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

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public StudentDetailDTO() {
    }

    public StudentDetailDTO(boolean gender, Date dateOfBirth, String phoneNum, String address, String facebook, byte[] avatar) {
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNum = phoneNum;
        this.address = address;
        this.facebook = facebook;
        this.avatar = avatar;
    }
    // Getters and Setters
}
