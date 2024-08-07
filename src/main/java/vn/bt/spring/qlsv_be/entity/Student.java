package vn.bt.spring.qlsv_be.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "ho_dem", length = 256)
    private String hoDem;
    @Column(name = "ten", length = 256)
    private String ten;
    @Column(name = "email", length = 256,unique = true)
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_detail_id")
    private StudentDetail studentDetail;

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

    public StudentDetail getStudentDetail() {
        return studentDetail;
    }

    public void setStudentDetail(StudentDetail studentDetail) {
        this.studentDetail = studentDetail;
    }

    public Student() {
    }

    public Student(String hoDem, String ten, String email, StudentDetail studentDetail) {
        this.hoDem = hoDem;
        this.ten = ten;
        this.email = email;
        this.studentDetail = studentDetail;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", hoDem='" + hoDem + '\'' +
                ", ten='" + ten + '\'' +
                ", email='" + email + '\'' +
                ", studentDetail=" + studentDetail +
                '}';
    }
}
