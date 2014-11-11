package Project;
abstract class Book
{
	public String generateUID();
	public abstract void generateForm();
	public abstract void displayStatus(String UID);
	public abstract void cancelRequest(String UID);
}

