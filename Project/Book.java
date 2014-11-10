package Project;
abstract class Book
{
	public String generateUID()
	{
		String[] letters={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		String[] nos={"0","1","2","3","4","5","6","7","8","9"};
		int len1 = letters.length;
		int len2 = nos.length;
		int rand1=(int)(Math.random()*len1);
		int rand2=(int)(Math.random()*len2);
		int rand3=(int)(Math.random()*len1);
		int rand4=(int)(Math.random()*len2);
		return letters[rand1]+nos[rand2]+letters[rand3]+nos[rand4];		
	}
	public abstract void generateForm();
	public abstract void displayStatus(String UID);
	public abstract void cancelRequest(String UID);
}

