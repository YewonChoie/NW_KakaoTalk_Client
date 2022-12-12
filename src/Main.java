import Object.ServerInfo;
import Utilization.Util;

public class Main {
    public static void main(String[] args) {
        ServerInfo info = Util.getServerInfo();
        BeforeLogin beforeLogin = new BeforeLogin(info);
    }
}