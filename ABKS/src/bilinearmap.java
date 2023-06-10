import it.unisa.dia.gas.jpbc.Element;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.awt.peer.CheckboxMenuItemPeer;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class bilinearmap {
    public static BigInteger randBigInter() {
        String[] POOL = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
        StringBuilder s = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 32; i++) {
            s.append(POOL[rand.nextInt(POOL.length)]);
        }
        return new BigInteger(s.toString(),16);
    }

    private static SecretKeySpec getSecretKey(final String password) {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = null;

        try {
            //构造密钥生成器，指定为AES算法,不区分大小写
            kg = KeyGenerator.getInstance("AES");
            //初始化SecureRandom，使用SHA1PRNG算法，SecureRandom类提供加密的强随机数生成器
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            //设置种子
            random.setSeed(password.getBytes());
            //AES 要求密钥长度为 128
            kg.init(128, random);

            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            // 转换为AES专用密钥
            return new SecretKeySpec(secretKey.getEncoded(), "AES");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String encrypt(String content, String password) {
        try {
            //根据指定算法AES创建密码器
            byte iv[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            // 初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY，
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password), ivspec);
            // 获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            // 加密
            byte[] result = cipher.doFinal(byteContent);
            //通过Base64转码返回
            return Functions.bytes_to_hexStr(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String content, String password) {

        try {
            //实例化，根据指定算法AES创建密码器
            byte iv[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password), ivspec);
            //执行操作
            byte[] result = cipher.doFinal(Functions.hexStr_to_bytes(content));

            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }




    public static void main(String[] args) throws NoSuchAlgorithmException {
        //2q+1=p
        /*BigInteger p = new BigInteger("13842871565919238596595605146446430605727");
        BigInteger g = new BigInteger("1131761502477326527402584075946863580236");
        BigInteger q = new BigInteger("6921435782959619298297802573223215302863");
        BigInteger a = g.modPow(new BigInteger("2"),p);
        System.out.println(a);
        a = g.modPow(q,p);
        System.out.println(a);
        BigInteger x = randBigInter();
        System.out.println(x);*/

        /*igInteger[] randnum = AASetup.buildFx(3, a);
        for (int i = 0; i<3;i++){
            System.out.println(randnum[i]);
        }
        BigInteger res = AASetup.getS(randnum, g, 3);
        System.out.println(res);*/
        /*BigInteger p = new BigInteger("730750818665451621361119245571504901405976559617");
        BigInteger g = new BigInteger("2");
        BigInteger q = new BigInteger("6921435782959619298297802573223215302863");
        BigInteger a = g.modPow(new BigInteger("2"),p);
        System.out.println(p.nextProbablePrime());

        System.out.println(g);
        BigInteger x = randBigInter();
        System.out.println(x);*/
        String waw = "wad1d21娃娃";
        Element a = UI.Zp.newRandomElement();
        String wawa = a.toString();
        System.out.println(wawa);
        String w = encrypt(waw, wawa);
        System.out.println(w);
        String c = decrypt(w, wawa);
        System.out.println(c);

        Element ppp = UI.G1.newRandomElement();
        System.out.println(ppp);
        byte[] p1 = ppp.toBytes();

        Element p2 = UI.G1.newElementFromBytes(p1);
        System.out.println(p2);
    }


}
