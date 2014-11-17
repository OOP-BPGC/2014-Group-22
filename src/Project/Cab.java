package Project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cab {
	/*
	 *Cab number is the unique attribute of each cab.
	 */
	private String cabNumber;
	private String cabType;
	private String licensePlate;
	private int capacity;
	private String driver;
	private String driverPhone;
	private int booked; // Stores current number of bookings issued with the cab
	
	public Cab()
	{
		this.booked = 0;
	}
	
	public void incrementBooked()
	{
		this.booked++;
	}
	
	public void decrementBooked()
	{
		this.booked--;
	}
	
	public int getBooked()
	{
		return this.booked;
	}
	
	public void setCabNumber(String cbn)
	{
		cabNumber=cbn;
	}
	public void setBooked(int b)
	{
		booked = b;
	}

	public void setCabType(String cbt)
	{
		cabType=cbt;
	}
	public void setLicensePlate(String lcsp)
	{
		licensePlate=lcsp;
	}
	public void setDriver(String name)
	{
		driver = name;
	}
	public boolean setDriverPhone(String ph)
	{
		Pattern pat = Pattern.compile("^\\d{10}$");
		Matcher match = pat.matcher(ph);
		if(match.find(0))
		{
			driverPhone = ph;
			return true;
		}
		else
		{
			// System.out.println("Failed to set Phone Number. Enter a valid 10 digit phone number.");
			return false;
		}
	}	
	public void setCapacity(int n)
	{
		capacity = n;
	}
	public String getCabNumber()
	{
		return cabNumber;
	}
	public String getCabType()
	{
		return cabType;
	}
	public String getDriver()
	{
		return driver;
	}
	public String getDriverPhone()
	{
		return driverPhone;
	}
	public int getCapacity()
	{
		return capacity;
	}
	public String getLicensePlate()
	{
		return licensePlate;
	}
}