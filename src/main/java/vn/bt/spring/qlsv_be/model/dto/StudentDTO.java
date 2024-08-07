package vn.bt.spring.qlsv_be.model.dto;

public class StudentDTO {
    private int id;
    private String hoDem;
    private String ten;
    private String email;
    private StudentDetailDTO studentDetail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoDem() {
        return hoDem;
    }

    public void setHoDem(String hoDem) {
        this.hoDem = hoDem;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StudentDetailDTO getStudentDetail() {
        return studentDetail;
    }

    public void setStudentDetail(StudentDetailDTO studentDetail) {
        this.studentDetail = studentDetail;
    }

    public StudentDTO() {
    }

    public StudentDTO(String hoDem, String ten, String email, StudentDetailDTO studentDetail) {
        this.hoDem = hoDem;
        this.ten = ten;
        this.email = email;
        this.studentDetail = studentDetail;
    }
    // Getters and Setters
}
