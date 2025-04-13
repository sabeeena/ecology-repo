package kz.iitu.se242m.yesniyazova.notifications.email;

public class SmtpClient {

    private final String host;
    private final String user;
    private final String password;

    private boolean connected = false;

    public SmtpClient(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
    }

    public void connect() {
        this.connected = true;
        System.out.printf("[SMTP] Connected to %s as %s%n", host, user);
    }

    public void disconnect() {
        this.connected = false;
        System.out.println("[SMTP] Disconnected from mail server");
    }

    public boolean isConnected() {
        return connected;
    }

    public void send(String to, String subject, String body) {
        if (!connected) {
            throw new IllegalStateException("SMTP client not connected");
        }
        System.out.printf("[SMTP] Sent email to %s with subject '%s'\n", to, subject);
    }
}
