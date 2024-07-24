package com.hims.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.hims.model.User;
import com.hims.repository.InventoryItemsRepository;
import com.hims.repository.OrderRepository;
import com.hims.repository.UserRepository;
import com.hims.service.MyUserService;
import com.hims.jwt.JwtConfig;
import com.hims.model.InventoryItems;
import com.hims.model.Login;
import com.hims.model.OrderCreate;
import com.hims.model.Orders;

@RestController
@CrossOrigin

public class Controller {
	
	@Autowired
	private UserRepository userRepo;
	
	//@Autowired
	private OrderCreate orderCreate;
	
	@Autowired
	private InventoryItemsRepository inventoryItemsRepo;
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private MyUserService myUserService;
	
	User user = new User();

	Orders order=new Orders();
	
	InventoryItems inventoryItems=new InventoryItems();
	
	@Autowired
	private JwtConfig jwtService;
	
	private int providerId;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerController(@RequestBody User userDetails) {
		try {
			User user1=null;
			user1=userRepo.findUserByEmail(userDetails.getEmail());
			if(user1!=null) {
				return new ResponseEntity<> ("User already exist with Email : "+userDetails.getEmail(),HttpStatus.ACCEPTED);
			}
			else{
				long x=userRepo.count();
				int id=(int)x+1;
				user.setId(id);
				user.setFirstName(userDetails.getFirstName());
				user.setLastName(userDetails.getLastName());
				user.setEmail(userDetails.getEmail());
				user.setPassword(new BCryptPasswordEncoder(10).encode(userDetails.getPassword()));
				userRepo.save(user);
				return new ResponseEntity<> ("User save with Email : "+userDetails.getEmail(),HttpStatus.CREATED);
			}
		}
		catch(Exception e) {
			return new ResponseEntity<> ("User not saved "+e,HttpStatus.BAD_REQUEST);
			
		}
		
	}
	@GetMapping("/alluser") 
    public List<User> getAllUser(){
        return userRepo.findAll();  
    }
	
	@GetMapping("/print") 
    public String print(){
        return "PRINT";  
    }
	
	
	@PostMapping("/loginn")
	public ResponseEntity<String> loginController(@RequestBody Login login) {
		User user1=null;
		user1=userRepo.findUserByEmail(login.getEmail());
		if(user1==null) {
			return new ResponseEntity<> ("User not registered with Email : "+login.getEmail()+"\nPlease Register!",HttpStatus.ACCEPTED);
		}
		else{
		try {
			System.out.println(login.getEmail()+" Runnnnnning "+login.getPassword());
			authManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(),login.getPassword()));
			final User user=myUserService.loadUserByUsername(login.getEmail());
			String token=jwtService.generateToken(user);
			providerId=user.getId();
			return new ResponseEntity<>(token,HttpStatus.OK);
		}
		catch(Exception e) {
			
			System.out.println("Error: "+e);
			return new ResponseEntity<>("Login Not done.Bad Credentials!",HttpStatus.BAD_REQUEST);
		}
		}
	}

	@GetMapping("/")
	public String message() {
		return "Integration Successful";
	}
	
	@PostMapping("/validate")
	public String validateJWT(@RequestHeader("Authorization")  String token) {
		token=token.substring(7);

		try{
		final User user1=myUserService.loadUserByUsername(jwtService.extractUserName(token));
		Boolean bool=jwtService.validateToken(token, user1);
		if(bool==true) {
			return "Token is valid";
		}
	}
	catch(Exception e) {
		e.printStackTrace();
	}
		
			return "Token is not valid";
		
		
	}
	
	@PostMapping("/saveinvitems")
	public InventoryItems inventoryItems(@RequestBody InventoryItems itemsDetails) {
		try {
			inventoryItemsRepo.save(itemsDetails);
			return itemsDetails;
		}
		catch(Exception e) {
			System.out.println("All items error");
			return itemsDetails;
			
		}
	}
	
	@GetMapping("/allitems") 
    public List<InventoryItems> getAllItems(){
        return inventoryItemsRepo.findAll();  
    }
	
	@PostMapping("/createorder")
	public String createOrder(@RequestBody OrderCreate[] x) {
		
		try {
			int z=0;
			String belowQuantity="";
			int y=0;
			String belowReorderThreshold="";

			String m="m ";

			for(int i=0;i<x.length; i++) {
				if (x[i]!=null){
					int productId=x[i].getProductId();
					int quantity=x[i].getQuantity();
					Optional<InventoryItems> invItem=inventoryItemsRepo.findById(productId);
					int quantityFromMongo = invItem.get().getQuantityAvailable();
					int reorderThreshold = invItem.get().getReorderThreshold();
					if(quantityFromMongo>=quantity) {
						if(quantityFromMongo-quantity<reorderThreshold){
							y=y+1;
							belowReorderThreshold=belowReorderThreshold+" "+invItem.get().getItemName();
						}
						
						//return "Order is not created. Quantity is not available for product id: "+productId;
					}
					else{
						z=1;
						belowQuantity=belowQuantity+" "+invItem.get().getItemName();

					}
					
				}
			}

			if(z==0){
			for(int i=0;i<x.length; i++) {
				if (x[i]!=null){
					//System.out.println(x[i].getProductId()+" "+x[i].getQuantity());
					int productId=x[i].getProductId();
					int quantity=x[i].getQuantity();
					Optional<InventoryItems> invItem=inventoryItemsRepo.findById(productId);
					//InventoryItems invItem=inventoryItemsRepo.findById(null);
					int quantityFromMongo = invItem.get().getQuantityAvailable();
					//System.out.println(quantityFromMongo);
				
					if(quantityFromMongo>=quantity){
						inventoryItems.setId(invItem.get().getId());
						inventoryItems.setDescription(invItem.get().getDescription());
						inventoryItems.setItemName(invItem.get().getItemName());
						inventoryItems.setReorderThreshold(invItem.get().getReorderThreshold());
						inventoryItems.setUnitPrice(invItem.get().getUnitPrice());
						inventoryItems.setQuantityAvailable(quantityFromMongo-quantity);
						inventoryItemsRepo.save(inventoryItems);
					
					}
				}
			}
			}
			if(z==0) {
				if(y>0) {
					m="Order is created but Quantity is below reorder threshold for products: "+belowReorderThreshold;
				}
				else {
					m="Order is created";
				}
			}
			else {
				m="Order is not created. Quantity is not available for product id: "+belowQuantity;
			}
			
			return m;
		}
		catch (Exception e) {
			System.out.println("exception "+e);
			return "Order is not created"+e;
		}
		
	}
	
	@PostMapping("/order")
	public ResponseEntity<Orders> order() {
		long x=orderRepo.count();
		int id=(int)x+1;
		order.setId(id);
		order.setProviderId(providerId);
		LocalDate date=LocalDate.now();
		order.setOrderDate(date.toString());
		order.setStatus("Pending");
		orderRepo.save(order);
		return new ResponseEntity<>(order,HttpStatus.CREATED);
	}
	
	@GetMapping("/allorders") 
    public List<Orders> getAllOrders(){
        return orderRepo.findAll();  
    }

}
