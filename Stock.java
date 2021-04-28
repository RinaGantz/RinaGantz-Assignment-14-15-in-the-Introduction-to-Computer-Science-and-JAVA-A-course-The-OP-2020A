
/**
 * This class represents the food items at the stock
 *
 * @author Rina Gantz
 * @version 15/01/2020
 */
public class Stock
{
    //attributes and declaration
    private FoodItem[] _stock;
    private int _noOfItems; 
    public final static int MAX_CELL=100;
    public final static int INVAL=0;

    /**
     * creates a new Stock object with the default values- 100 cell in the array and 0 items
     */
    public Stock()
    {
        _noOfItems=INVAL;
        _stock=new FoodItem[MAX_CELL];

    }

    /**
     * gets the num of the items 
     *
     * @return the num of the items
     */
    public int  getNumOfItems()
    {
        return _noOfItems;
    }

    /**
     * add item to array. If the product already exists in the array - add the quantity of the new item to it.
     * other - Add the new item to the array in the appropriate cell.
     * @param newItem a new food item to add the array
     * @ return true if it adds the item to the array and false if the array is already full
     */
    public boolean addItem(FoodItem newItem){
        for (int i=0;i<_noOfItems&&_stock[i]!=null;i++){
            if(_stock[i].getCatalogueNumber()>=newItem.getCatalogueNumber()){//check which cell is  the appropriate cell to add the new item to
                if(newItem.equals(_stock[i])){//do add the new item to the same food item that is already in stock
                    _stock[i].setQuantity(_stock[i]. getQuantity()+newItem.getQuantity());
                    return true;
                }
                for(int j=_noOfItems;j>i&&j!=MAX_CELL;j--)//move all cells to make place for the new item
                    _stock [j]=_stock[j-1];
                _stock[i]=new FoodItem(newItem);
                _noOfItems++;
                return true;
            }
        }
        if (_noOfItems==MAX_CELL)
            return false;
        _stock[_noOfItems]=new FoodItem(newItem);//add the new item to the appropriate cell in stock
        _noOfItems++;
        return true;
    }

    /**
     * returns a String that represents the stock order

     * @return String that represents the stock order in the following format:
     * food item, food item
     */
    public String order(int amount)
    {
        String s="";
        int newAmount=amount;
        for (int i=0; i<_noOfItems; i++)
        {
            if(_stock[i]. getQuantity()<amount){//the order quantity is more than the quantity of the item in one cell
                newAmount-=_stock[i]. getQuantity();
                for (int j=i+1; j<_noOfItems; j++){//how many products exists in stock from the item
                    if(_stock[i].getCatalogueNumber()==_stock[j].getCatalogueNumber()) {
                        newAmount-=_stock[j]. getQuantity();
                    }
                }
            }
            if (newAmount!=amount&&newAmount>INVAL)//add the missing product name
                s+=_stock[i].getName()+", ";
            newAmount=amount;
        }
        if (s.length()==INVAL)
            return s;
        return s.substring(0,s.length()-2);
    }

    /**
     * check how many products can store at the range refrigerator temperature
     * @return how many products can store at the range refrigerator temperature
     */
    public int howMany(int temp)
    {
        int store=INVAL;
        for (int i=0; i<_noOfItems; i++)//count how many products can store at the given temperature
            if(_stock[i].getMinTemperature()<=temp&&_stock[i].getMaxTemperature()>=temp)
                store+=_stock[i].getQuantity();
        return store;
    }

    /**
     * check if the food items is fresh on the date d, and remove the outdate items
     * @param d date to check
     */
    public void removeAfterDate (Date d) {
        for(int i=0; i<_noOfItems;i++)
            if(_stock[i].getExpiryDate().before(d)){
                for(int j=i; j<_noOfItems-1;j++)//remove the outdate items and fill the hole
                    _stock[j]=_stock[j+1];
                i--;
                _stock[_noOfItems-1]=null;
                _noOfItems--;
            }
    }

    /**
     * check who is the most expensive food item in the stock
     * @return the most expensive food item in the stock
     */
    public FoodItem mostExpensive() 
    {
        if(_noOfItems==INVAL)
        return null;
        int most=0;
        for (int i=0;i<_noOfItems;i++)
            if (_stock[i].getPrice()>_stock[most].getPrice())
                most=i; 
        return new FoodItem(_stock[most]);
    }

    /**
     * count all the quantity of the prodacts in stock
     *
     * @return all the quantity of the prodacts in stock
     */
    public int howManyPieces()
    {
        int products=INVAL;
        for (int i=0;i<_noOfItems;i++)
            products+=_stock[i].getQuantity();
        return products;
    }

    /**
     * returns a String that represents the food items that in the stock
     * @return String that represents the food items that in the in the stock, in the following format:
     * FoodItem: milk CatalogueNumber: 1234 ProductionDate: 14/12/2019 
     * ExpiryDate: 21/12/2019 Quantity: 3
     */
    public String toString()
    {
        String s="";
        for(int i=0; i<_noOfItems;i++)
            s+=_stock[i]+"\n";
        return s;
    }

    /**
     * remove from the stock food items that sold and doesn't exist anymore
     * @param itemsList string of items to remove from the stock
     */
    public void updateStock(String[] itemsList){
        for (int i=0;i<itemsList.length;i++)
        {
            for(int j=0;j<_noOfItems;j++)
            {
                if(_stock[j].getName().equals(itemsList[i]))//remove from the stock food item that existed in the stock and had been sold 
                {
                    {
                        _stock[j].setQuantity(_stock[j].getQuantity()-1);
                    }
                    if (_stock[j].getQuantity()==INVAL){//If there is a hole then fill it
                        for(int k=j;k<_noOfItems-1;k++)
                            _stock [k]=_stock[k+1]; 
                        _stock[_noOfItems-1]=null;
                        _noOfItems--;
                    }
                    break;}
            }
        }
    }

    /**
     * return the minimum storage temperature that fits all products, and if there is none, return the Integer.MAX_VALUE
     * @return the storage temperature 
     */
    public int getTempOfStock()
    {
        if (_noOfItems!=INVAL){
            int min=_stock[0].getMinTemperature(),max=_stock[0].getMaxTemperature();
            for(int i=0;i<_noOfItems;i++){
                if(_stock[i].getMinTemperature()>=min)//check what is the lowest maximum temperature
                    min=_stock[i].getMinTemperature();
                if(_stock[i].getMaxTemperature()<=max)//check what is the highest minimum temperature
                    max=_stock[i].getMaxTemperature();
            }
            if (max-min>=INVAL)//there is a storage range suitable for all products
                return min;
        }
        return Integer.MAX_VALUE;//there is no storage range suitable for all products
    }
}