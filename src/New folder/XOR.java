package cs380;

public class XOR 
{
	
	public static String xorMessage(String in, String keyString) 
	{
		char[] key = new char[keyString.length()];
		for(int i = 0; i < keyString.length(); i++)
		{
			key[i] = keyString.charAt(i);
		}
		
		StringBuilder out = new StringBuilder();
		
		for(int i = 0; i < in.length(); i++) {
			out.append((char) (in.charAt(i) ^ key[i % key.length]));
		}
 		
		return out.toString();
	}
	
	public static void main(String[] args) {
	}
}