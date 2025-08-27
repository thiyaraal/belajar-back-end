package com.example.demo.controller;
import com.example.demo.entity.Todos;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.*;
import java.util.List;
import com.example.demo.repository.TodoRepository;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    
    //get
    @GetMapping(value="/find-todo")
    public List<Todos> getAllTodos() {
        return todoRepository.findAll();
    }
   

    //create
    @PostMapping(value="/create-todo")
    public Todos createTodo(@RequestBody Todos todo) {
        return todoRepository.save(todo);
    }

    // edit
    @PutMapping("/update-todo/{id}")
    public Todos updateTodo(@PathVariable Long id, @RequestBody Todos todo) {
        todo.setId(id);
        return todoRepository.save(todo);
    }

    // delete
    @DeleteMapping("/delete-todo/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }

}
