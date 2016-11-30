package cs380;

public class XOR 
{
	
	private static sha test;
	
	private static StringBuilder xorMessage(String in) 
	{
		
		char[] key = {'A'};
		
		StringBuilder out = new StringBuilder();
		
		for(int i = 0; i < in.length(); i++) {
			out.append((char) (in.charAt(i) ^ key[i % key.length]));
		}
 		
		return out;
	}
	
	public static void main(String[] args) {
		String original = XOR.xorMessage("MessageToEncrypt").toString();
		System.out.println(original);
		String decrypted = XOR.xorMessage(original).toString();
		System.out.println(decrypted);
		
		test = new sha(original);
		//System.out.println(test.hash);
		
	}
}