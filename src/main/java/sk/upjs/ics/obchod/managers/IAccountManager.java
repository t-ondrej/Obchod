package sk.upjs.ics.obchod.managers;

import javafx.beans.property.BooleanProperty;
import sk.upjs.ics.obchod.entity.Account;

public interface IAccountManager {
    
    public boolean signInPerson(String username, String password);
    
    public void signUpPerson(String username, String password);
    
    Account getActiveAccount();
    
    BooleanProperty isSignedIn();
    
    void logOutPerson();
    
    boolean isUsernameAvailable(String username);
    
    void changePassword(Account account, String password);
    
    void setActiveAccount(Account account);
    
    void setSignedIn(BooleanProperty signedIn);
    
    Account findById(Long id);
}
