package com.ecommerce.user.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.user.dto.AddressDTO;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.entity.Address;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	//private List<User> userList = new ArrayList<>();
	
	
	/*//instead of this constructors we can use @RequiredArgsConstructor this handle everything
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}*/
	
	public List<UserResponse>fetchAllUsers(){
		return userRepository.findAll().stream().map(this::mapToUserResponse).collect(Collectors.toList());
	}
	
	
	public void addUser(UserRequest userRequest){
		
		
		User user = new User();
		updateUserFromRequest (user,userRequest);
		userRepository.save(user);
		
	}
	


	public Optional<UserResponse> fetchUser(Long id) {
					/*for(User user: userList) {			this is java v8 old thing we use for loop to find user
						if(user.getId().equals(id)) {
							return user;
						}
					}
					return null;
				}*/
		//moredern syntax in java and more readable
		//return userList.stream().filter(user-> user.getId().equals(id)).findFirst();
		
		return userRepository.findById(id).map(this::mapToUserResponse);
	}
	
	public boolean updateUser(Long id, UserRequest updateUserRequest) {
		//return userList.stream().filter(user -> user.getId().equals(id)).findFirst().map(existingUser -> {
		
		return userRepository.findById(id).map(existingUser ->{
		updateUserFromRequest (existingUser, updateUserRequest);				/*existingUser.setFirstName(updatedUser.getFirstName()); existingUser.setLastName(updatedUser.getLastName()); */  //this is manual
			
			
			userRepository.save(existingUser);
			return true;
		}).orElse(false);
	}
	

	private void updateUserFromRequest (User user, UserRequest userRequest) {
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setEmail(userRequest.getEmail());
		user.setPhone(userRequest.getPhone());
		
		if(userRequest.getAddress()!=null) {
			Address address = new Address();
			
			address.setStreet(userRequest.getAddress().getStreet());
			address.setCity(userRequest.getAddress().getCity());
			address.setState(userRequest.getAddress().getState());
			address.setCountry(userRequest.getAddress().getCountry());
			address.setPincode(userRequest.getAddress().getPincode()); 
			user.setAddress(address);
		}
	}
	
	
	private UserResponse mapToUserResponse(User user) {
		UserResponse response = new UserResponse();
		
		response.setId(String.valueOf(user.getId()));
		response.setFirstName(user.getFirstName());
		response.setLastName(user.getLastName());
		response.setEmail(user.getEmail());
		response.setPhone(user.getPhone());
		response.setRole(user.getRole());
		
		if(user.getAddress()!=null) {
			AddressDTO addressDTO = new AddressDTO();
			
			addressDTO.setStreet(user.getAddress().getStreet());
			addressDTO.setCity(user.getAddress().getCity());
			addressDTO.setState(user.getAddress().getState());
			addressDTO.setCountry(user.getAddress().getCountry());
			addressDTO.setPincode(user.getAddress().getPincode()); 
			response.setAddress(addressDTO);
		}
		return response;
		
	}
	
}
