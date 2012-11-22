package models;

import play.db.ebean.Model;
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class User extends Model {
 
 		@Id
    public String email;

    public String password;
    public String fullname;
    public boolean isAdmin;
    
    public User(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }

    public static User connect(String email, String password) {
    	return find("byEmailAndPassword", email, password).first();
		}

}