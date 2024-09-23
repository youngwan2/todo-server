package com.todolist.server.service;

import com.todolist.server.entity.Todo;
import com.todolist.server.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public List<Todo> findAll(){
        return  todoRepository.findAll();

    }
}
