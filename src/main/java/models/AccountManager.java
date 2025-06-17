package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    Map<String,String> accounts;


    public AccountManager() {
        accounts = new HashMap<String,String>();
    }

    public boolean checkIfAccountExists(String account) {
        return accounts.containsKey(account);
    }

    public boolean isCorrectPas(String accName, String pass){
        if(checkIfAccountExists(accName)){
            String hashedPass=hash(pass);
            if(hashedPass.equals(accounts.get(accName))){return true;}
            return false;
        }
        return false;
    }
    public void createAccount(String accName, String pass){
        if(checkIfAccountExists(accName)){
            System.out.println("Account already exists");
            return;
        }
        String hashedPass=hash(pass);
        accounts.put(accName, hashedPass);
    }
    private String hash(String password) {
        MessageDigest digest;
        try {
            digest=MessageDigest.getInstance("SHA");
            digest.update(password.getBytes());
            return hexToString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i=0; i<bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;  // remove higher bits, sign
            if (val<16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }

}
