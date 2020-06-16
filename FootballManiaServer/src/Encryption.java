import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
  
  public static String createMD5(String input){
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      byte[] hashBytes = messageDigest.digest(input.getBytes());
      BigInteger number = new BigInteger(1, hashBytes);      
      String hashText = number.toString(16);      
      
      //Jeżeli hashText jest krótszy niż 32 znaki, wtedy zrób prefix z zer, tak żeby było razem 32 znaki.
      while (hashText.length() < 32) {
        hashText = "0" + hashText;
      }
      
      return hashText;
    }
    catch (NoSuchAlgorithmException e){   
      throw new RuntimeException(e);
    }
  }
  
  public static String createSHA256(String input){
    try {                  
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
      byte[] hashBytes = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));               
      
      StringBuilder strigBuilder = new StringBuilder();
      
      for (int i = 0; i < hashBytes.length; i++){
        strigBuilder.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      
      return strigBuilder.toString();
    }
    catch (NoSuchAlgorithmException e){   
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    System.out.println(createMD5("P@ssw0rd"));
  }
}
