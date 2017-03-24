package sk.upjs.ics.obchod.managers;

import java.time.LocalDateTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import sk.upjs.ics.obchod.entity.Cart;
import sk.upjs.ics.obchod.entity.User;
import sk.upjs.ics.obchod.dao.IUserDao;

public class UserManager implements IUserManager {

    private final IUserDao userDao;
    private User signedInUser;
    private BooleanProperty isSignedIn = new SimpleBooleanProperty(false);

    public UserManager(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getSignedInUser() {
        return signedInUser;
    }

    @Override
    public BooleanProperty isSignedIn() {
        return isSignedIn;
    }

    @Override
    public void logOutUser() {
        this.signedInUser = null;
        isSignedIn.setValue(!isSignedIn.getValue());
    }

    @Override
    public boolean signInUser(String username, String password) {
        User user = userDao.findByName(username);

        if (user != null && user.checkPassword(password)) {
            Cart cart = user.getCart();
            user.setCart(cart);
            user.setLastLogin(LocalDateTime.now());
            userDao.updateLastLogin(user);

            signedInUser = user;
            isSignedIn.setValue(!isSignedIn.getValue());
            return true;
        }

        return false;
    }

    @Override
    public void signUpUser(String username, String password, String email,
            String name, String surname, String city, String street, int postalCode) {
        User user = new User();
        user.setLogin(username);
        user.setPassword(password);
        user.setEmail(email);

        user.setName(name);
        user.setSurname(surname);
        user.setCity(city);
        user.setStreet(street);
        user.setPostalCode(postalCode);

        user.setLastLogin(LocalDateTime.now());

        Cart cart = new Cart();
        user.setCart(cart);

        userDao.saveOrUpdate(user);
    }

    @Override
    public boolean isUsernameAvailable(String name) {
        User user = userDao.findByName(name);
        return user == null;
    }

    @Override
    public void save(User user) {
        userDao.saveOrUpdate(user);
    }

    @Override
    public void changePassword(User user, String password) {
        signedInUser.setPassword(password);
        userDao.saveOrUpdate(user);
    }

    @Override
    public void setSignedInUser(User signedInUser) {
        this.signedInUser = signedInUser;
    }

    @Override
    public void setSignedIn(BooleanProperty isSignedIn) {
        this.isSignedIn = isSignedIn;
    }

    @Override
    public boolean hasFilledBillingAddress() {
        return signedInUser.getName() != null && signedInUser.getSurname() != null
                && signedInUser.getCity() != null && signedInUser.getStreet() != null
                && signedInUser.getPostalCode() > -1;
    }
}
