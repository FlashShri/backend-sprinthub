

package com.sprinthub.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sprinthub.dto.LoginDTO;
import com.sprinthub.entity.Admin;
import com.sprinthub.entity.Employee;
import com.sprinthub.entity.Manager;
import com.sprinthub.entity.UserInfo;
import com.sprinthub.repository.AdminRepository;
import com.sprinthub.repository.EmployeeRepository;
import com.sprinthub.repository.ManagerRepository;
import com.sprinthub.repository.UserInfoRepository;




@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository  userInfoRepository;

    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private ManagerRepository managerRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder encoder;
    
    @Autowired
    private UserInfoRepository userRepository ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userDetail = userInfoRepository.findByEmail(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public Object addUser(UserInfo userInfo) {
    	
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        Object obj = null ;
        String role = userInfo.getRole();
        
        
        // Simultaneously save to role-specific table based on the role
        switch (role.toLowerCase()) {
            case "admin":
                Admin admin = new Admin();
                admin.setEmail(userInfo.getEmail()); // assuming email is used as adminName
                admin.setPassword(userInfo.getPassword());
                
                
                 obj =  adminRepository.save(admin);
                
                break;
           
            case "manager":
                Manager manager = new Manager();
                manager.setEmail(userInfo.getEmail()); // assuming email is used as adminName
                manager.setPassword(userInfo.getPassword());
                obj =  managerRepository.save(manager);
                break;    
                
            case "empolyee":
                Employee employee = new Employee();
                employee.setEmail(userInfo.getEmail()); // assuming email is used as adminName
                employee.setPassword(userInfo.getPassword());
                obj = employeeRepository.save(employee);
                break;   
                
            // Handle other roles if needed
            default:
                // Handle default case if needed
        }

       // return "User Added Successfully";
        
        return obj ;
    }

	public LoginDTO getUserData(String email) {
		// TODO Auto-generated method stub
		
	 	Optional<UserInfo> user = userRepository.findByEmail(email);
	 	
	 	 UserInfo u = user.get();
	 	 LoginDTO ldto = new LoginDTO();
	 	 ldto.setRole(u.getRole());
	 	 ldto.setId(u.getId());
	 	 
	 	 
		
		return ldto;
	}
}

