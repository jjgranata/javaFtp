package cs380;

import java.util.Scanner;

public class Base64 {
	public Base64() {
	}
	public static boolean promptUser() {
		Scanner kb = new Scanner(System.in);
		char userAnswer = 'a';
	
		while( (userAnswer != 'y') && (userAnswer != 'n') ) {
			System.out.println("Would you like to Ascii armor with MIME Base64 encoding before sending? (y/n):");
			userAnswer = kb.next().charAt(0);
		}
		if(userAnswer == 'y') {
			kb.close();
			return true;
		}
		else {
			kb.close();
			return false;
		}
	}

	public static String encode(byte[] data) {
		char[] tbl = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
				'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
				'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', '+', '/' };

		StringBuilder buffer = new StringBuilder();
		int pad = 0;
		for (int i = 0; i < data.length; i += 3) {

			int b = ((data[i] & 0xFF) << 16) & 0xFFFFFF;
			if (i + 1 < data.length) {
				b |= (data[i + 1] & 0xFF) << 8;
			} else {
				pad++;
			}
			if (i + 2 < data.length) {
				b |= (data[i + 2] & 0xFF);
			} else {
				pad++;
			}

			for (int j = 0; j < 4 - pad; j++) {
				int c = (b & 0xFC0000) >> 18;
				buffer.append(tbl[c]);
				b <<= 6;
			}
		}
		for (int j = 0; j < pad; j++) {
			buffer.append("=");
		}

		return buffer.toString();
	}

	public static byte[] decode(String encoded) throws RuntimeException {
		byte[] base64Alphabet = new byte[255];
		for (int i = 0; i < 255; i++) {
			base64Alphabet[i] = -1;
		}
		for (int i = 'Z'; i >= 'A'; i--) {
			base64Alphabet[i] = (byte) (i - 'A');
		}
		for (int i = 'z'; i >= 'a'; i--) {
			base64Alphabet[i] = (byte) (i - 'a' + 26);
		}

		for (int i = '9'; i >= '0'; i--) {
			base64Alphabet[i] = (byte) (i - '0' + 52);
		}
		base64Alphabet['+'] = 62;
		base64Alphabet['/'] = 63;

		byte[] base64Data = encoded.getBytes();
		int len = removeWhiteSpace(base64Data);
		// should be divisible by four
		if (len % 4 != 0) {
			throw new RuntimeException("decoding.divisible.four");
		}

		int numberQuadruple = (len / 4);

		if (numberQuadruple == 0)
			return new byte[0];

		byte decodedData[] = null;
		byte b1 = 0, b2 = 0, b3 = 0, b4 = 0;

		int i = 0;
		int encodedIndex = 0;
		int dataIndex = 0;

		dataIndex = (numberQuadruple - 1) * 4;
		encodedIndex = (numberQuadruple - 1) * 3;

		b1 = base64Alphabet[base64Data[dataIndex++]];
		b2 = base64Alphabet[base64Data[dataIndex++]];
		if ((b1 == -1) || (b2 == -1)) {
			throw new RuntimeException("decoding.general");
		}

		byte d3, d4;
		b3 = base64Alphabet[d3 = base64Data[dataIndex++]];
		b4 = base64Alphabet[d4 = base64Data[dataIndex++]];
		if ((b3 == -1) || (b4 == -1)) {
			// Check if they are pad characters
			if (isPad(d3) && isPad(d4)) { // Two pad
				if ((b2 & 0xf) != 0)// last 4 bits should be zero
					throw new RuntimeException("decoding.general");
				decodedData = new byte[encodedIndex + 1];
				decodedData[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
			} else if (!isPad(d3) && isPad(d4)) { // One pad
				if ((b3 & 0x3) != 0)// last 2 bits should be zero
					throw new RuntimeException("decoding.general");
				decodedData = new byte[encodedIndex + 2];
				decodedData[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
				decodedData[encodedIndex] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
			} else {
				throw new RuntimeException("decoding.general");
			}
		} else {
			// No padding
			decodedData = new byte[encodedIndex + 3];
			decodedData[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
			decodedData[encodedIndex++] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
			decodedData[encodedIndex++] = (byte) (b3 << 6 | b4);
		}
		encodedIndex = 0;
		dataIndex = 0;
		// the beginning
		for (i = numberQuadruple - 1; i > 0; i--) {
			b1 = base64Alphabet[base64Data[dataIndex++]];
			b2 = base64Alphabet[base64Data[dataIndex++]];
			b3 = base64Alphabet[base64Data[dataIndex++]];
			b4 = base64Alphabet[base64Data[dataIndex++]];

			if ((b1 == -1) || (b2 == -1) || (b3 == -1) || (b4 == -1)) {
				throw new RuntimeException("decoding.general");
			}

			decodedData[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
			decodedData[encodedIndex++] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
			decodedData[encodedIndex++] = (byte) (b3 << 6 | b4);
		}
		return decodedData;
	}

	protected static int removeWhiteSpace(byte[] data) {
		if (data == null)
			return 0;

		// count characters that are not whitespace
		int newSize = 0;
		int len = data.length;
		for (int i = 0; i < len; i++) {
			byte dataS = data[i];
			if (!isWhiteSpace(dataS))
				data[newSize++] = dataS;
		}
		return newSize;
	}

	protected static boolean isPad(byte octect) {
		return (octect == '=');
	}

	protected static boolean isWhiteSpace(byte octect) {
		return (octect == 0x20 || octect == 0xd || octect == 0xa || octect == 0x9);
	}

	public static void main(String[] args) {
//		promptUser();
//		byte[] a = { 1, 1, 1, 1, 1, 1, 1 };
//		System.out.println(encode(a));
//		byte[] b = decode(encode(a));
//
//		for (int i = 0; i < b.length; i++) {
//			System.out.println(b[i]);
//		}
	}
}
