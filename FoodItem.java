
/**
 * This class represents a FoodItem object. 
 *
 * @author Rina Gantz
 * @version 30/12/2019
 */
public class FoodItem
{
    // attributes and declarations
    private String _name;
    private long _catalogueNumber;
    private int _quantity;
    private Date _productionDate;
    private Date _expiryDate;
    private  int _minTemperature;
    private int _maxTemperature;
    private int _price;
    public static final int  DEFAULT_QUANTITY=0;
    public static final int  MIN_CATALOGUE_NUM=1000;
    public static final int  MAX_CATALOGUE_NUM=9999;
    public static final int  DEFAULT_PRICE=1;
    public static final int  INVAL=0;
    public static final String DEFAULT_NAME="item";

    /**
     * creates a new FoodItem object
     * @param name name of food item
     * @param catalogueNumber catalogue number of food item
     * @param quantity quantity of food item
     * @param productionDate production date
     * @param expiryDate expiry date
     * @param minTemperature minimum storage temperature
     * @param maxTemperature maximum storage temperature
     * @param price unit price
     */
    public FoodItem (String name, long catalogueNumber,  int quantity, Date productionDate, Date expiryDate, int minTemperature, int maxTemperature, int price )

    {
        //Set the basic parameters value.
        _name=name;
        _catalogueNumber=catalogueNumber;
        _quantity=quantity;
        _productionDate=new Date(productionDate);
        _expiryDate=new Date(expiryDate);
        _minTemperature=minTemperature;
        _maxTemperature=maxTemperature;
        _price=price;

        //Check if the parameters are legal parameters
        if (expiryDate.before(productionDate))
            _expiryDate= productionDate.tomorrow();
        if (minTemperature>maxTemperature)
        {
            _minTemperature=maxTemperature;
            _maxTemperature=minTemperature;
        }
        if (name.equals(""))
            _name=DEFAULT_NAME;
        if (catalogueNumber>MAX_CATALOGUE_NUM||catalogueNumber<MIN_CATALOGUE_NUM)
            _catalogueNumber=MAX_CATALOGUE_NUM;
        if (quantity<DEFAULT_QUANTITY)
            _quantity=DEFAULT_QUANTITY;
        if (price<=INVAL)
            _price=DEFAULT_PRICE;

    }

    /**
     * copy constructor
     * @param other the food item to be copied
     */
    public FoodItem (FoodItem other)
    {
        //Make a copy for the date
        _name=other._name;
        _catalogueNumber=other._catalogueNumber;
        _quantity=other._quantity;
        _productionDate=new Date(other._productionDate);
        _expiryDate=new Date(other._expiryDate);
        _minTemperature=other._minTemperature;
        _maxTemperature=other._maxTemperature;
        _price=other._price;
    }
    //methods

    /**
     * gets the catalogue number
     * @return the catalogue number
     */
    public long getCatalogueNumber()
    {
        return _catalogueNumber;
    }

    /**
     * gets the name
     * @return the name
     */
    public String getName()
    {
        return _name;
    }

    /**
     * gets the quantity
     * @return the quantity
     */
    public int getQuantity()
    {
        return _quantity;
    }

    /**
     * gets the production date 
     * @return the production date
     */
    public Date getProductionDate()
    {
        return new Date(_productionDate);
    }

    /**
     * gets the expiry date 
     * @return the expiry date
     */
    public Date getExpiryDate()
    {
        return new Date(_expiryDate);
    }

    /**
     * gets the minimum storage temperature
     * @return the minimum storage temperature
     */
    public int getMinTemperature()
    {
        return _minTemperature ;
    }

    /**
     * gets the maximum storage temperature
     * @return the maximum storage temperature
     */
    public int getMaxTemperature ()
    {
        return _maxTemperature ;
    }

    /**
     * gets the unit price
     * @return the unit price
     */
    public int getPrice ()
    {
        return _price ;
    }

    /**
     * set the quantity (only if not negative)
     * @param n the quantity value to be set
     */
    public void setQuantity (int n) 
    {
        if (n>=INVAL)
            _quantity=n;
    }

    /**
     * set the production date (only if not after expiry date )
     * @param d - production date value to be set
     */

    public void setProductionDate (Date d) 
    {

        if (!d.after(_expiryDate))
            _productionDate=new Date(d);
    }

    /**
     * set the expiry date (only if not before production date )
     * @param d expiry date value to be set
     */
    public void setExpiryDate (Date d) 
    {

        if (!_productionDate.after(d))
            _expiryDate= new Date(d);
    }

    /**
     * set the price (only if positive)
     * @param n price value to be set 
     */
    public void setPrice (int n)
    {
        if (n>INVAL)
            _price=n;
    }

    /**
     * check if 2 food items are the same (excluding the quantity values)
     * @param other the food item to compare this food item to
     * @return true if the food items are the same
     */
    public boolean equals (FoodItem other)
    {
        return (other._name.equals(this._name)&&other._catalogueNumber==this._catalogueNumber&&other._productionDate.equals(this._productionDate)&&
            other._expiryDate.equals(this._expiryDate)&&other._minTemperature==this._minTemperature&&other._maxTemperature==this._maxTemperature&&other._price==this._price);
    }

    /**
     * check if this food item is fresh on the date d
     * @param d date to check
     * @return true if this food item is fresh on the date d
     */
    public boolean isFresh (Date d) 
    {
        return (d.after(_productionDate)&&d.before(_expiryDate)||d.equals(_productionDate)||d.equals(_expiryDate));
    }

    /**
     * returns a String that represents this food item
     * @return String that represents this food item in the following format:
     * FoodItem: milk CatalogueNumber: 1234 ProductionDate: 14/12/2019 
     * ExpiryDate: 21/12/2019 Quantity: 3
     */
    public String toString()
    {
      
        String s="FoodItem: "+_name+"\tCatalogueNumber: "+_catalogueNumber+"\tProductionDate: "+_productionDate+"\tExpiryDate: "+
        _expiryDate+"\tQuantity: "+_quantity;
        return s;

    }

    /**
     * check if this food item is older than other food item
     * @param other food item to compare this food item to
     * @return true if this food item is older than other date 
     */
    public boolean olderFoodItem (FoodItem other)
    {
        return other._productionDate.after(this._productionDate);

    }

    /**
     * returns the number of units of products that can be purchased for a given amount
     * @param amount amount to purchase
     * @return the number of units can be purchased
     */
    public int howManyItems(int amount)
    {
        int numUnits=amount/_price;
        //Check for the maximum value of the two
        if (numUnits>_quantity)
            return _quantity;
        return numUnits;
    }

    /**
     * check if this food item is cheaper than other food item
     * @param other food item to compare this food item to
     * @return true if this food item is cheaper than other date
     */
    public boolean isCheaper (FoodItem other)
    {
        return (this._price<other._price);
    }

}//end of class FoodItem
