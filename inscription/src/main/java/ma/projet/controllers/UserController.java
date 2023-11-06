package ma.projet.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ma.projet.entities.User;
import ma.projet.repository.UserRepository;
import ma.projet.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
//@RequiredArgsConstructor
public class UserController {
	
	@Autowired
	private UserService userService;
	

	@GetMapping
	public List<User> findAllUser(){
		return userService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		User user = userService.findById(id);
		if(user == null) {
			return new ResponseEntity<Object>("User with ID " + id + " not found", HttpStatus.BAD_REQUEST);
		}
		else {
			return ResponseEntity.ok(user);
		}
	}
	
	@PostMapping
	public User createUser(@RequestBody User user) {
		user.setId(0L);
		return userService.create(user);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable Long id,@RequestBody User user) {
//		Filiere filiere = filiereService.findById(id);
		if(userService.findById(id) == null) {
			return new ResponseEntity<Object>("User with ID " + id + " not found", HttpStatus.BAD_REQUEST);
		}
		else {
			user.setId(id);
			return ResponseEntity.ok(userService.update(user));
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable Long id){
		User user = userService.findById(id);
		if(user == null) {
			return new ResponseEntity<Object>("User with ID " + id + " not found", HttpStatus.BAD_REQUEST);
		}
		else {
			userService.delete(user);
			return ResponseEntity.ok("User has been deleted");
		}
	}
}
