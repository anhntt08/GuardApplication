package safeobject.model;

public class Auth {
    private String token;
    private long expire;

    public Auth() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "token='" + token + '\'' +
                ", expire=" + expire +
                '}';
    }
}
