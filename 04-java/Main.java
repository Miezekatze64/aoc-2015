import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {
	public static final String KEY = "yzbqklnj";
	public static void main(String[] args) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			long i = 0;
			for (;;) {
				i++;
				if (byteArrayToHex(md5.digest((KEY + i).getBytes())).startsWith("00000")) {
					break;
				}
			}
			System.out.println("Part 1: " + i);
			i = 0;
			for (;;) {
				i++;
				if (byteArrayToHex(md5.digest((KEY + i).getBytes())).startsWith("000000")) {
					break;
				}
				if (i % 10000 == 0) System.out.println(i);
			}
			System.out.println("Part 2: " + i);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static String byteArrayToHex(byte[] a) {
 	  StringBuilder sb = new StringBuilder(a.length * 2);
	   for(byte b: a)
	      sb.append(String.format("%02x", b));
	   return sb.toString();
	}
}
