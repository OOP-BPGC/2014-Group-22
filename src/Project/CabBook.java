package Project;

/**
 * Abstract class which implements Book class and has
 * CabBookDestinationBased, CabBookDistanceBased and CabBookTimeBased
 * as its subclasses
 * @author nisarg
 */
public abstract class CabBook extends Book {

	// All common variables to the three sub classes
	protected String initialDate;
	protected String finalDate;
	protected String initialTime;
	protected String finalTime;
	protected Cab cab;
	protected String bookingType;
	protected String bookedBy; // Stores the id of user who booked it
	//
	
	public CabBook()
	{
		super();
		cab = null;
		bookedBy = Login.id;
	}
	
	public String getInitialTime() {
		return initialTime;
	}

	public void setInitialTime(String initialTime) {
		this.initialTime = initialTime;
	}

	public String getFinalTime() {
		return finalTime;
	}

	public void setFinalTime(String finalTime) {
		this.finalTime = finalTime;
	}

	public Cab getCab() {
		return this.cab;
	}

	public void setCab(Cab cab) {
		this.cab = cab;
	}

	public String getBookingType() {
		return this.bookingType;
	}

	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}

	public String getBookedBy() {
		return this.bookedBy;
	}

	public void setBookedBy(String bookedBy) {
		this.bookedBy = bookedBy;
	}

	public void setInitialDate(String initialDate) {
		this.initialDate = initialDate;
	}

	public void setFinalDate(String finalDate) {
		this.finalDate = finalDate;
	}
	
	public String getInitialDate()
	{
		return this.initialDate;
	}
	
	public String getFinalDate()
	{
		return this.finalDate;
	}
	
	
	// TODO: Generate request id that determines which type of booking was used
	/**
	 * Queries the databases for the given UID and removes the corresponding booking
	 * @param UID
	 */
	public void cancelRequest(String UID)
	{
		CabDB.readAllFromDB();
		
		for(int i = 0; i < CabDB.CabListDest.size(); i++)
		{
			if(CabDB.CabListDest.get(i).getUID().equals(UID))
			{
				CabDB.CabListDest.remove(i);
				return;
			}
		}
		for(int i = 0; i < CabDB.CabListDist.size(); i++)
		{
			if(CabDB.CabListDist.get(i).getUID().equals(UID))
			{
				CabDB.CabListDist.remove(i);
				return;
			}
		}
		for(int i = 0; i < CabDB.CabListTime.size(); i++)
		{
			if(CabDB.CabListTime.get(i).getUID().equals(UID))
			{
				CabDB.CabListTime.remove(i);
				return;
			}
		}
		CabDB.writeAllToDB(); // Write changes to DB
	}

	// TODO: Decide whether we need this method
	public void displayStatus(String Uid)
	{
		CabDB.readAllFromDB();
		
		for(int i = 0; i < CabDB.CabListDest.size(); i++)
		{
			if(CabDB.CabListDest.get(i).getUID().equals(UID))
			{
				CabDB.CabListDest.get(i);
				return;
			}
		}
		for(int i = 0; i < CabDB.CabListDist.size(); i++)
		{
			if(CabDB.CabListDist.get(i).getUID().equals(UID))
			{
				CabDB.CabListDist.get(i);
				return;
			}
		}
		for(int i = 0; i < CabDB.CabListTime.size(); i++)
		{
			if(CabDB.CabListTime.get(i).getUID().equals(UID))
			{
				CabDB.CabListTime.get(i);
				return;
			}
		}
	}
	
	public abstract int calcFare();
}
