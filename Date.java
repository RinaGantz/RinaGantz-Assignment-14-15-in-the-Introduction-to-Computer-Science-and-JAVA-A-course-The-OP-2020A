
/**
 * This class represents a Date object
 *
 * @author Rina Gantz
 * @version 18/12/2019
 */
public class Date
{
    //// attributes and declaration
    private int _day;
    private int _month;
    private int _year;
    public final static  int MONTH_OF_YEAR=12;
    /**
     * creates a new Date object if the date is valid, otherwise creates the date 1/1/2000
     * @param day the day in the month (1-31)
     * @param month the month in the year (1-12)
     * @param year the year (4 digits)
     * 
     */
    public Date(int day, int month, int year)
    {
        //declarations
        final int DEFAULT_DAY=01;
        final int DEFAULT_MONTH=01;
        final int DEFAULT_YEAR=2000;

        if (checkDate(day,month,year))//Check if the parameters are legal parameters
        {
            _day=day;
            _month=month;
            _year=year;
        }
        else//if the parameters are illegal parameters
        {
            _day=DEFAULT_DAY;
            _month=DEFAULT_MONTH;
            _year=DEFAULT_YEAR;
        }

    }

    /**
     * copy constructor
     * @param other the date to be copied
     */
    public Date(Date other)
    {
        _day=other._day;
        _month=other._month;
        _year=other._year;

    }
    //methods

    /**
     * gets the day
     * @return The day
     */
    public int getDay()
    {
        return _day;
    }

    /**
     *gets the month
     * @return The month
     */
    public int getMonth()
    {
        return _month;

    }

    /**
     *  gets the year
     * @return The year
     */
    public int getYear()
    {
        return _year;

    }

    // Checks if a given date is in a valid and return true or false
    private boolean checkDate(int day, int month,int year){
        //declarations
        final int FULL_MONTH=31,MISSING_MONTH=30,LEAP_FEB=28,MAX_LEAP_FEB=29,MIN_MONTH=1,MIN_DAY=1,INVAL=0, MAX_YEAR=9999,MIN_YEAR=1000,
        JAN=1,FEB=2,APR=4,JUNE=6,SEP=9,NOV=11,CHECK_LEAP1=4, CHECK_LEAP2=100,CHECK_LEAP3=400;
        if(year>=MIN_YEAR&&year<=MAX_YEAR){
            if(month>=MIN_MONTH&&month<=MONTH_OF_YEAR)
                if  (month==APR|| month==JUNE || month==SEP||month ==NOV){//Check the months with 30 days
                    if (day<=MISSING_MONTH&&day>=MIN_DAY)
                        return true;
                }
                else{//Check the months with 31 days
                    if (month!=FEB)
                        if(day<=FULL_MONTH&&day>=MIN_DAY)
                            return true;
                    //Check if febuary is in a leap year with 29 days or ordinary year with 28 days 
                    if (((year%CHECK_LEAP1==0&&year%CHECK_LEAP2>0||year%CHECK_LEAP3==0)&&day>=MIN_MONTH&&day<=MAX_LEAP_FEB)
                    ||day>=MIN_MONTH&&day<=LEAP_FEB)
                        return true;
                }
        }
        return false;//The given parameters can't constitute a legal date
    }

    /**
     * sets the day (only if date remains valid)
     * @param dayToSet the day value to be set
     */
    public void setDay(int dayToSet)
    {
        if(checkDate(dayToSet,_month,_year))
        {
            _day=dayToSet;
        }
    }

    /**
     * sets the month (only if date remains valid)
     * @param monthToSet the month value to be set
     */
    public void setMonth(int monthToSet)
    {
        if(checkDate(_day,monthToSet,_year))
        {
            _month=monthToSet;
        }
    }

    /**
     *sets the year (only if date remains valid)
     * @param yearToSet the year value to be set
     */
    public void setYear(int yearToSet)
    {
        if(checkDate(_day,_month,yearToSet))
        {
            _year=yearToSet;
        }
    }

    /** 
     *  check if 2 dates are the same
     * @param other the date to compare this date to
     * @return true if they are equal, oterwise false. 
     */
    public boolean equals(Date other)
    {
        return (other._day==_day&&other._month==_month&&other._year==_year);
    }

    /** 
     * check if this date is before other date
     * @param other date to compare this date to
     * @return true if the dates are the same
     */
  
        public boolean before(Date other)
    {
        //Check which date is the first date according to the years, months and days of the two dates
        if (_year<other._year)
            return true;
        if (_year>other._year)
            return false;
        if (_month<other._month)
            return true;
        if (_month>other._month)
            return false;
        if (_day<other._day)
            return true;
        return false;
    }

    /** 
     * check if this date is after other date
     * @param other date to compare this date to
     * @rtrue if this date is after other date
     * 
     */
    public boolean after(Date other)

    {
        return other.before(this);
    }

    /**
     * calculates the difference in days between two dates
     * @param other the date to calculate the difference between
     * @return the number of days between the dates
     */
    public int difference(Date other)
    {
        //Calculate the difference between the dates according to the method calculateDate
        int otherDate=calculateDate(other._day,other._month,other._year);
        int thisDate=calculateDate(this._day,this._month,this._year);
        return Math.abs(otherDate-thisDate);

    }
    // computes the day number since the beginning of the Christian counting of years
    private int calculateDate ( int day, int month, int year) {
        if (month < 3) {
            year--;
            month = month + 12;
        }
        return 365 * year + year/4 - year/100 + year/400 + ((month+1) * 306)/10 + (day - 62);
    } 

    /**
     * returns a String that represents this date
     * @return String that represents this date in the following format: day/month/year for example: 02/03/1998
     */
    public String toString()
    {
        final int CHECK_STRING=10;
        String s = "";
        //Check if to add zeros before the num in the print
        if (_day<CHECK_STRING)
            s = s+"0";
        s = s + _day +"/";
        if (_month<CHECK_STRING)
            s = s+"0";
        s = s +_month+"/"+_year;

        return s;
    }

    /**
     * calculate the day of the week that this date occurs on 0-Saturday 1-Sunday 2-Monday etc.
     * @return the day of the week that this date occurs on
     */
    public int dayInWeek()
    {
        //Declarations
        int calculMon=_month;
        int calculYear=_year;
        final int CHECK_MONTH=3;
        if (_month<CHECK_MONTH)//Check if the month is jenuary or febuary
        {
            calculMon+=MONTH_OF_YEAR;
            calculYear--;
        }
      
        return  Math.floorMod((_day + (26*(calculMon+1))/10 + calculYear%100 + calculYear%100/4 + calculYear/100/4 - 2*(calculYear/100)),7);
    }

    /**
     *calculate the date of tomorrow
     * @return the date of tomorrow
     */
    public Date tomorrow()
    {
        int ADD_ONE=1;
        //Check all the options of the next day according to the method that check the date
        if (checkDate(_day+ADD_ONE,_month,_year))
            return new Date (_day+ADD_ONE,_month,_year);
        if (checkDate(ADD_ONE,_month+ADD_ONE,_year))
            return new Date (ADD_ONE,_month+ADD_ONE,_year);
        return new Date (ADD_ONE,ADD_ONE,_year+ADD_ONE);
    }
}//end of class Date
