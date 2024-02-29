package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.exception.ResourceNotFoundException;
import org.example.model.Task;
import org.example.model.TaskStatus;
import org.example.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Класс TaskController, который обрабатывает HTTP-запросы к приложению
 */
@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    /**
     * Внедрение зависимости репозитория
     */
    private TaskRepository taskRepository;



    /**
     * Метод для добавления задачи
     * @param task
     * @return
     */
    @PostMapping
    public Task addTask(@RequestBody Task task) {
        // Устанавливаем статус задачи по умолчанию как "не начата"
        task.setStatus(TaskStatus.NOT_STARTED);
        // Сохраняем задачу в базе данных и возвращаем ее
        return taskRepository.save(task);
    }

    /**
     * Метод для просмотра всех задач
     * @return
     */
    @GetMapping
    public List<Task> getAllTasks() {
        // Возвращаем список всех задач из базы данных
        return taskRepository.findAll();
    }

    /**
     * Метод для просмотра задач по статусу
     * @param status
     * @return
     */
    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable TaskStatus status) {
        // Возвращаем список задач с указанным статусом из базы данных
        return taskRepository.findByStatus(status);
    }

    /**
     * Метод для изменения статуса задачи
     * @param id
     * @param task
     * @return
     */
    @PutMapping("/{id}")
    public Task updateTaskStatus(@PathVariable Long id, @RequestBody Task task) {
        // Находим задачу по ID в базе данных
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        // Устанавливаем новый статус задачи из тела запроса
        existingTask.setStatus(task.getStatus());
        // Сохраняем измененную задачу в базе данных и возвращаем ее
        return taskRepository.save(existingTask);
    }

    /**
     * Метод для удаления задачи
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        // Удаляем задачу по ID из базы данных
        taskRepository.deleteById(id);
    }
}
