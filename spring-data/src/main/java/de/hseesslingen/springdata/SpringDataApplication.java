package de.hseesslingen.springdata;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController	
public class SpringDataApplication {

	@Autowired
	TodoRepository todoRepository;

	@Value("${application.name:not_set}")
	private String appName;

	@Autowired
	private ApplicationProperties ap;

	@GetMapping("/value")
	String value(){
		return appName;
	}

	@GetMapping("/appNameViaPOJO")
	String appNameViaPOJO() {
		return this.ap.getName();
	}
	

	@GetMapping("/")
	String getTodos(){

		ArrayList<String> todos = new ArrayList<String>();
		todoRepository.findAll().forEach(todo -> todos.add(todo.getTodo()));
		return todos.toString();
	}

	@PostMapping("/{todo}")
	String addTodo(@PathVariable String todo){

		todoRepository.save(new Todo(todo));
		return "added "+todo;
	}

	@DeleteMapping("/{todo}")
	String removeTodo(@PathVariable String todo) {

		todoRepository.deleteById(todo);
		return "removed "+todo;

	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}
}

@Component
@ConfigurationProperties("application")
class ApplicationProperties {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

@Entity
class Todo{

	@Id
	String todo;

	public Todo(){}

	public Todo(String todo){ 
		this.todo = todo; 
	}

	public String getTodo(){ 
		return todo; 
	}

	public void setTodo(String todo) { 
		this.todo = todo; 
	}

}

interface TodoRepository extends CrudRepository<Todo, String> {
 
}
