package com.example.dto;

import com.example.model.Role;
import com.example.model.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Boolean builtIn;

    private Set<String> roles;


    public void setRoles(Set<Role> roles){
        Set<String> rolesStr=new HashSet<>();

        roles.forEach(r->{
            if (r.getName().equals(RoleType.ROLE_ADMIN))
                rolesStr.add("Administrator");
            else if (r.getName().equals(RoleType.ROLE_CORPORATE))
                rolesStr.add("Corporate");
            else
                rolesStr.add("Individual");
        });

        this.roles=rolesStr;
    }
}
