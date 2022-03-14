import org.mindrot.jbcrypt.BCrypt;

public class Main {
    public static void main(String[] args) {
        //注册：传入明文密码
        //1.得到盐值
//        String plainPassword = "123456";
//        String salt = BCrypt.gensalt();
//        System.out.println(salt);
//        String s = BCrypt.hashpw(plainPassword,salt);
//        System.out.println(s);

        //$2a$10$lqqQOtrPlfp7EtKkfxG8Y.wnEubK6J.sF24SCp0qbGuNSDk3RhuR2

        String plainPassword = "123455";
        String hashpw = "$2a$10$lqqQOtrPlfp7EtKkfxG8Y.wnEubK6J.sF24SCp0qbGuNSDk3RhuR2";
        boolean checkpw = BCrypt.checkpw(plainPassword, hashpw);
        System.out.println(checkpw);
    }
}
