package sk.upjs.ics.obchod.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product extends Entity {


    private StringProperty name = new SimpleStringProperty();

    private ObjectProperty<Brand> brand = new SimpleObjectProperty<>();

    private ObjectProperty<Category> category = new SimpleObjectProperty<>();

    private IntegerProperty price = new SimpleIntegerProperty();

    private StringProperty description = new SimpleStringProperty();
    
    private StringProperty imagePath = new SimpleStringProperty();  
    
    private IntegerProperty quantity = new SimpleIntegerProperty();

    public Product() {
    }

    public Product(Long id, String name, Brand brand, Category category, int price, 
            String description, String imagePath, int quantity) {
        super(id);
        this.name.setValue(name);
        this.brand.setValue(brand);
        this.category.setValue(category);
        this.price.setValue(price);
        this.description.setValue(description);
        this.imagePath.setValue(imagePath);
        this.quantity.setValue(quantity);
    }
    
    public Product(Long id, String name, Brand brand, Category category, int price, 
            int quantity) {
        super(id);
        this.name.setValue(name);
        this.brand.setValue(brand);
        this.category.setValue(category);
        this.price.setValue(price);
        this.quantity.setValue(quantity);
    }
    
    public String getName() {
        return name.getValue();
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public Brand getBrand() {
        return brand.getValue();
    }

    public void setBrand(Brand brand) {
        this.brand.setValue(brand);
    }

    public ObjectProperty<Brand> brandProperty() {
        return brand;
    }

    public Category getCategory() {
        return category.getValue();
    }

    public void setCategory(Category category) {
        this.category.setValue(category);
    }

    public ObjectProperty<Category> categoryProperty() {
        return category;
    }

    public int getPrice() {
        return price.getValue();
    }

    public void setPrice(int price) {
        this.price.setValue(price);
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public String getDescription() {
        return description.getValue();
    }

    public void setDescription(String description) {
        this.description.setValue(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getImagePath() {
        return imagePath.getValue();
    }

    public void setImagePath(String imagePath) {
        this.imagePath.setValue(imagePath);
    }

    public StringProperty imagePathProperty() {
        return imagePath;
    }

    public int getQuantity() {
        return quantity.getValue();
    }

    public void setQuantity(int quantity) {
        this.quantity.setValue(quantity);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    @Override
    public int hashCode() {
        int hash = this.getPrice();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Product other = (Product) obj;

        return this.getName().equals(other.getName());

    }
}
