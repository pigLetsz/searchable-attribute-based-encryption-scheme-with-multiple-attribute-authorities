import it.unisa.dia.gas.jpbc.Element;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

public class Functions {
    //  十六进制字符串转字节串，字符串长度为偶数
    public static byte[] hexStr_to_bytes(String s) {
        byte[] bytes = new byte[s.length() / 2];
        for (int i = 0; i < s.length(); i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return bytes;
    }

    //  字节串转十六进制字符串
    public static String bytes_to_hexStr(byte[] bytes) {
        final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] chars = new char[bytes.length * 2];
        for (int i = 0, j = 0; i < bytes.length; i++) {
            chars[j++] = hexDigits[bytes[i] >> 4 & 0x0f];
            chars[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(chars);
    }

    public static Element Hash1(String s) throws NoSuchAlgorithmException{
        byte[] b = s.getBytes();
        MessageDigest hasher = MessageDigest.getInstance("SHA-256");
        byte[] b2 = hasher.digest(b);
        Element hash = UI.Zp.newElement().setFromHash(b2, 0, b2.length);
        return hash;
    }

    public static Element Hash2(String s) throws NoSuchAlgorithmException{
        byte[] b = s.getBytes();
        byte[] b1 = new byte[b.length * 2];
        System.arraycopy(b, 0, b1, 0, b.length);
        System.arraycopy(b, 0, b1, b.length, b.length);
        MessageDigest hasher = MessageDigest.getInstance("SHA-256");
        byte[] b2 = hasher.digest(b1);
        Element hash = UI.Zp.newElement().setFromHash(b2, 0, b2.length);
        return hash;
    }

    public static Element Hash3(String s) throws NoSuchAlgorithmException{
        byte[] b = s.getBytes();
        byte[] b1 = new byte[b.length * 3];
        System.arraycopy(b, 0, b1, 0, b.length);
        System.arraycopy(b, 0, b1, b.length, b.length);
        System.arraycopy(b, 0, b1, b.length * 2, b.length);
        MessageDigest hasher = MessageDigest.getInstance("SHA-256");
        byte[] b2 = hasher.digest(b1);
        Element hash = UI.Zp.newElement().setFromHash(b2, 0, b2.length);
        return hash;
    }

    public static int[] gainRandomT(int t, int n){
        int[] indexs = new int[t];
        Random random = new Random();
        Boolean haveIn;
        for(int i = 0; i < t; i++){
            haveIn = false;
            int x = random.nextInt(n);
            for(int j = 0; j < i; j++){
                if(indexs[j] == x) {
                    i -= 1;
                    haveIn = true;
                    break;
                }
            }
            if(!haveIn){
                indexs[i] = x;
            }
        }
        return indexs;
    }

    public static Element[][] Mor(Element[][] A, Element[][] B){
        int da = A.length;
        int ea = A[0].length;
        int db = B.length;
        int eb = B[0].length;
        int row = da + db;
        int column = ea + eb - 1;

        Element[][] result = new Element[row][column];
        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++) {
                result[i][j] = UI.Zp.newZeroElement();
            }
        }

        for(int i = 0; i < da; i++){
            result[i][0] = A[i][0].duplicate();
        }
        for(int i = 0; i < db; i++){
            result[i + da][0] = B[i][0].duplicate();
        }
        for(int i = 0; i < da; i++){
            for(int j = 1; j < ea; j++){
                result[i][j] = A[i][j].duplicate();
            }
        }
        for(int i = 0; i < db; i++){
            for(int j = 1; j < eb; j++){
                result[i + da][j + ea - 1] = B[i][j].duplicate();
            }
        }
        return result;
    }

    public static Element[][] Mand(Element[][] A, Element[][] B){
        int da = A.length;
        int ea = A[0].length;
        int db = B.length;
        int eb = B[0].length;
        int row = da + db;
        int column = ea + eb;

        Element[][] result = new Element[row][column];
        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++) {
                result[i][j] = UI.Zp.newZeroElement();
            }
        }
        for(int i = 0; i < da; i++){
            result[i][0] = A[i][0].duplicate();
        }
        for(int i = 0; i < da; i++){
            result[i][1] = A[i][0].duplicate();
        }
        for(int i = 0; i < db; i++){
            result[i + da][1] = B[i][0].duplicate();
        }
        for(int i = 0; i < da; i++){
            for(int j = 1; j < ea; j++){
                result[i][j + 1] = A[i][j].duplicate();
            }
        }
        for(int i = 0; i < db; i++){
            for(int j = 1; j < eb; j++){
                result[i + da][j + ea] = B[i][j].duplicate();
            }
        }
        return result;
    }

    public static Element[][] getLSSSM(String logic, ArrayList<List<String>> accessStructureList){
        Element[][] basicM = new Element[1][1];
        Element[][] midM;
        Element[][] finalM;
        basicM[0][0] = UI.Zp.newOneElement();
        if(logic.equals("或")){
            finalM = basicM;
            for(int i = 0; i < accessStructureList.size(); i++){
                midM = basicM;
                for(int j = 0; j < accessStructureList.get(i).size() - 1; j++){
                    midM = Functions.Mor(midM, basicM);
                }
                if(i == 0){
                    finalM = midM;
                }
                else{
                    finalM = Functions.Mand(finalM, midM);
                }
            }
        }
        else{
            finalM = basicM;
            for(int i = 0; i < accessStructureList.size(); i++){
                midM = basicM;
                for(int j = 0; j < accessStructureList.get(i).size() - 1; j++){
                    midM = Functions.Mand(midM, basicM);
                }
                if(i == 0){
                    finalM = midM;
                }
                else{
                    finalM = Functions.Mor(finalM, midM);
                }
            }
        }



        return finalM;
    }

    public static String[] getrou(ArrayList<List<String>> accessStructureList){
        int total = 0;
        for(int i = 0; i < accessStructureList.size(); i++){
            total += accessStructureList.get(i).size();
        }
        String[] res = new String[total];
        int count = 0;
        for(int i = 0; i < accessStructureList.size(); i++){
            for(int j = 0; j < accessStructureList.get(i).size(); j++){
                res[count] = accessStructureList.get(i).get(j);
                count ++;
            }
        }

        return res;
    }

    private static SecretKeySpec getSecretKey(final String password) {
        KeyGenerator kg = null;

        try {
            kg = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());
            kg.init(128, random);
            SecretKey secretKey = kg.generateKey();
            return new SecretKeySpec(secretKey.getEncoded(), "AES");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String encrypt(String content, String password) {
        try {
            byte iv[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password), ivspec);
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            byte[] result = cipher.doFinal(byteContent);
            return Functions.bytes_to_hexStr(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String content, String password) {

        try {
            byte iv[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password), ivspec);
            byte[] result = cipher.doFinal(Functions.hexStr_to_bytes(content));

            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Element> getOmega(ArrayList<Element[]> Mu){
        ArrayList<Element> omega = new ArrayList<Element>();
        int size = Mu.size();
        for(int i = 0; i < size; i++){
            omega.add(UI.Zp.newRandomElement());
        }
        Boolean rep = false;
        ArrayList<Element[]> tMu = transformMu(Mu);

        for(int i = 1; i < tMu.size(); i++){
            for(int j = 0; j < tMu.get(0).length; j++){
                if(!tMu.get(0)[j].equals(tMu.get(i)[j])){
                    break;
                }
                if(j == tMu.get(0).length - 1){
                    return omega;
                }
            }
        }

        if(judgeZero(tMu.get(0))){
            return omega;
        }

        for(int i = 0; i < tMu.size(); i++){
            if(judgeZero(tMu.get(i))){
                tMu.remove(i);
                i--;
            }
        }
        if(tMu.size() > tMu.get(0).length){
            return omega;
        }

        else{
            while (true){
                for(int i = 0; i < tMu.size(); i++){
                    if(judgeZero(tMu.get(i))){
                        tMu.remove(i);
                        i--;
                    }
                }
                for(int i = 0; i < tMu.size(); i++){
                    for(int j = 0; j < tMu.size(); j++){
                        rep = false;
                        if(i != j){
                            for(int k = 0; k < tMu.get(0).length; k++){
                                if(!tMu.get(i)[k].equals(tMu.get(j)[k])){
                                    break;
                                }
                                if(k == tMu.get(0).length - 1){
                                    rep = true;
                                }
                            }
                            if(rep){
                                tMu.remove(j);
                                j--;
                            }
                        }

                    }
                }

                if(tMu.size() == tMu.get(0).length){
                    break;
                }

                else{
                    for(int x = 0; x < tMu.size(); x++){
                        Element[] copy = new Element[tMu.size()];
                        System.arraycopy(tMu.get(x), 0, copy, 0, tMu.size());
                        tMu.set(x, copy);
                    }
                }

            }
            ArrayList<Element> omega2 = guass(tMu);
            if(omega2.size() == omega.size()){
                omega = omega2;
            }
            else{
                for(int i = 0; i < omega.size(); i++){
                    if(i < omega2.size()){
                        omega.set(i, omega2.get(i));
                    }
                    else{
                        omega.set(i, UI.Zp.newZeroElement());
                    }
                }
            }
        }
        return omega;

    }

    public static ArrayList<Element[]> transformMu(ArrayList<Element[]> Mu){
        ArrayList<Element[]> tMu = new ArrayList<Element[]>();
        for(int i = 0; i < Mu.get(0).length; i++){
            tMu.add(new Element[Mu.size()]);
        }
        for(int i = 0; i < Mu.size(); i++){
            for(int j = 0; j < Mu.get(0).length; j++){
                tMu.get(j)[i] = Mu.get(i)[j];
            }
        }
        return tMu;

    }

    public static ArrayList<Element[]> removeRepeatedRow(ArrayList<Element[]> Mu){
        for(int i = 0; i < Mu.size(); i++){
            for(int j = 0; j < Mu.size(); j++){
                if(j != i && Mu.get(j).equals(Mu.get(i))){
                    Mu.remove(j);
                    j --;
                }

            }
        }
        return Mu;
    }

    public static ArrayList<Element> guass(ArrayList<Element[]> M){
        int n = M.size();
        ArrayList<Element> omega = new ArrayList<Element>();
        omega.add(UI.Zp.newOneElement());
        for(int i = 1; i < M.size(); i++){
            omega.add(UI.Zp.newZeroElement());
        }
        if(n == 1){
            return omega;
        }
        else{
            int r = 0;
            for(int c = 0; c < n; c++){
                int t = r;
                for(int i = r; i < n; i++){
                    if(!M.get(i)[c].equals(UI.Zp.newZeroElement())){
                        t = i;
                        break;
                    }
                }
                if(t != r){
                    Collections.swap(M, r, t);
                    Collections.swap(omega, r, t);
                }
                omega.set(r, omega.get(r).div(M.get(r)[c]));
                for(int j = n - 1; j >= c; j--){
                    M.get(r)[j].div(M.get(r)[c]);
                }
                for(int i = r + 1; i < n; i++) {
                    omega.set(i, omega.get(i).sub(omega.get(r).duplicate().mulZn(M.get(i)[c])));
                    for (int j = n - 1; j  >= c; j--){
                        M.get(i)[j].sub(M.get(r)[j].duplicate().mulZn(M.get(i)[c]));
                    }
                    //omega.set(i, omega.get(i).duplicate().sub(omega.get(r).duplicate().mulZn(M.get(i)[c])));
                }
                r++;

            }
            for(int i = n - 1; i >= 1; i--){
                for(int j = i - 1; j >= 0; j--){
                    omega.set(j, omega.get(j).sub(omega.get(i).duplicate().mulZn(M.get(j)[i])));
                    M.get(j)[i].sub(M.get(i)[i].duplicate().mulZn(M.get(j)[i]));

                }

            }
        }

        return omega;

    }

    public static Boolean judgeZero(Element[] V){
        Boolean allZero = false;

        for(int i = 0; i < V.length; i++){
            if(!V[i].equals(UI.Zp.newZeroElement())){
                break;
            }
            if(i == V.length - 1){
                allZero = true;
            }
        }
        return allZero;
    }
}
