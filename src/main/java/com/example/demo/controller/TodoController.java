package com.example.demo.controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.entity.Todos;
import com.example.demo.repository.TodoRepository;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping(value = "/find-todo")
    public List<Todos> getAllTodos() {
        return todoRepository.findAll();
    }

    @PostMapping(value = "/create-todo")
    public Todos createTodo(@RequestBody Todos todo) {
        return todoRepository.save(todo);
    }

    @PutMapping("/update-todo/{id}")
    public Todos updateTodo(@PathVariable Long id, @RequestBody Todos todo) {
        todo.setId(id);
        return todoRepository.save(todo);
    }

    @DeleteMapping("/delete-todo/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }

}
