package com.example.homework.command;

import com.example.homework.dto.StudentRequestDTO;
import com.example.homework.service.StudentService;

public class SaveStudentCommand implements Command {

    private final StudentService service;
    private final StudentRequestDTO studentDTO;

    public SaveStudentCommand(StudentService service, StudentRequestDTO studentDTO) {
        this.service = service;
        this.studentDTO = studentDTO;
    }

    @Override
    public void execute() {
        service.saveStudent(studentDTO);
    }
}
