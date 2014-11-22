package src;

public class Cab {
	
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
	public void setBooked(int b)
	{
		booked = b;
	}
	public void setLicensePlate(String lcsp)
	{
		licensePlate=lcsp;
	}
	public void setDriver(String name)
	{
		driver = name;
	}
	public void setDriverPhone(String ph)
	{
		driverPhone = ph; // Implemented regex in calling method 		
	}	
	public void setCapacity(int n)
	{
		capacity = n;
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
	public String toString()
	{
		return("Cab license plate no. " + this.licensePlate + ", capacity = " + this.capacity);
	}
}
