package com.example.homework.command;

import com.example.homework.service.StudentService;

public class DeleteStudentCommand implements Command {
    private final StudentService service;
    private final String email;

    public DeleteStudentCommand(StudentService service, String email) {
        this.service = service;
        this.email = email;
    }

    @Override
    public void execute() {
        service.deleteStudent(email);
    }
}
