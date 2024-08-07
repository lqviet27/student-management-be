package vn.bt.spring.qlsv_be.model.mapper;

import vn.bt.spring.qlsv_be.entity.Student;
import vn.bt.spring.qlsv_be.entity.StudentDetail;
import vn.bt.spring.qlsv_be.model.dto.StudentDTO;
import vn.bt.spring.qlsv_be.model.dto.StudentDetailDTO;

import javax.sql.rowset.serial.SerialBlob;
import java.util.Base64;

public class StudentMapper {
    public static StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setHoDem(student.getHoDem());
        dto.setTen(student.getTen());
        dto.setEmail(student.getEmail());
        if (student.getStudentDetail() != null) {
            StudentDetailDTO detailDTO = new StudentDetailDTO();
            StudentDetail detail = student.getStudentDetail();
            detailDTO.setId(detail.getId());
            detailDTO.setGender(detail.isGender());
            detailDTO.setDateOfBirth(detail.getDateOfBirth());
            detailDTO.setPhoneNum(detail.getPhoneNum());
            detailDTO.setAddress(detail.getAddress());
            detailDTO.setFacebook(detail.getFacebook());
            if (detail.getAvatar() != null) {
                try {
                    byte[] avatarBytes = detail.getAvatar().getBytes(1, (int) detail.getAvatar().length());
                    detailDTO.setAvatar(Base64.getEncoder().encode(avatarBytes)); // Encode byte array to Base64
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            dto.setStudentDetail(detailDTO);
        }
        return dto;
    }

    public static Student convertToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setId(dto.getId());
        student.setHoDem(dto.getHoDem());
        student.setTen(dto.getTen());
        student.setEmail(dto.getEmail());
        if (dto.getStudentDetail() != null) {
            StudentDetail detail = new StudentDetail();
            StudentDetailDTO detailDTO = dto.getStudentDetail();
            detail.setId(detailDTO.getId());
            detail.setGender(detailDTO.isGender());
            detail.setDateOfBirth(detailDTO.getDateOfBirth());
            detail.setPhoneNum(detailDTO.getPhoneNum());
            detail.setAddress(detailDTO.getAddress());
            detail.setFacebook(detailDTO.getFacebook());
            if (detailDTO.getAvatar() != null) {
                try {
                    byte[] avatarBytes = Base64.getDecoder().decode(detailDTO.getAvatar()); // Decode Base64 to byte array
                    detail.setAvatar(new SerialBlob(avatarBytes)); // Convert byte array to Blob
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            student.setStudentDetail(detail);
        }
        return student;
    }
}
