package sk.upjs.ics.obchod.managers;

import java.time.LocalDateTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import sk.upjs.ics.obchod.dao.IAccountDao;
import sk.upjs.ics.obchod.dao.ICartDao;
import sk.upjs.ics.obchod.entity.Account;
import sk.upjs.ics.obchod.entity.Cart;

public class AccountManager implements IAccountManager {
    
    private IAccountDao accountDao;
    
    private Account activeAccount;
    
    private BooleanProperty isSignedIn = new SimpleBooleanProperty(false);

    public AccountManager(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }  
    
    @Override
    public boolean signInPerson(String username, String password) {
        Account account = accountDao.findByUsername(username);

        if (account != null && account.checkPassword(password)) {
            Cart cart = new Cart();
            account.setCart(cart);
            account.setLastLogin(LocalDateTime.now());
            accountDao.updateLastLogin(account);

            activeAccount = account;
            isSignedIn.setValue(!isSignedIn.getValue());
            return true;
        }

        return false;
    }

    @Override
    public void signUpPerson(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Account getActiveAccount() {
        return activeAccount;
    }

    @Override
    public BooleanProperty isSignedIn() {
        return isSignedIn;
    }

    @Override
    public void logOutPerson() {
        activeAccount = null;
        isSignedIn.setValue(!isSignedIn.getValue());
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        return accountDao.findByUsername(username) == null;
    }

    @Override
    public void changePassword(Account account, String password) {
        account.setPassword(password);
        accountDao.saveOrUpdate(account);
    }

    @Override
    public void setActiveAccount(Account account) {
        this.activeAccount = account;
    }

    @Override
    public void setSignedIn(BooleanProperty signedIn) {
        this.isSignedIn = signedIn;
    }
    
    @Override
    public Account findById(Long id) {
        return accountDao.findById(id);
    }
}
