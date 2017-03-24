package sk.upjs.ics.obchod.managers;

import javafx.beans.property.BooleanProperty;
import sk.upjs.ics.obchod.entity.User;

public interface IUserManager {
    
    public boolean signInUser(String username, String password);
    
    public void signUpUser(String username, String password, String email, 
            String name, String surname, String city, String street, int postalCode);
    
    User getSignedInUser();
    
    BooleanProperty isSignedIn();
    
    void logOutUser();
    
    boolean isUsernameAvailable(String username);
    
    void save(User user);
    
    void changePassword(User user, String password);
    
    void setSignedInUser(User user);
    
    void setSignedIn(BooleanProperty signedIn);
    
    boolean hasFilledBillingAddress();
}
