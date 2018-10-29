package de.hseesslingen.helloesslingen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@SpringBootApplication
@RestController
public class HelloEsslingenApplication {

	private ArrayList<String> todoList = new ArrayList<String>();

	@GetMapping("/hello")
	String sayHello(){

		return "Hello Esslingen!";
	}

	@GetMapping("/")
	String getTodos(){

		return todoList.toString();
	}

	@PostMapping("/{todo}")
	String addTodo(@PathVariable String todo){

		todoList.add(todo);
		return  todo+" added";
	}

	@DeleteMapping("/{todo}")
	String removeTodo(@PathVariable String todo){

		todoList.remove(todo);
		return  todo+" removed";
	}

	public static void main(String[] args) {
		SpringApplication.run(HelloEsslingenApplication.class, args);
	}
}
