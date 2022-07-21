package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if (studentByEmail.isPresent()) {
            throw new IllegalStateException("Email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        boolean existsStudent = studentRepository.existsById(id);
        if(!existsStudent) throw new IllegalStateException("User not found");
        studentRepository.deleteById(id);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("Student not found"));
        if(!email.isEmpty()) student.setEmail(email);
        if(!name.isEmpty()) student.setName(name);

        if(!email.isEmpty() && !name.isEmpty()){
            student.setEmail(email);
            student.setName(name);
        }

    }
}
