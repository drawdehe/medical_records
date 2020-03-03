import java.net.*;
import java.io.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;
import java.security.KeyStore;
import java.security.cert.*;
import java.util.Scanner;
/*
 * This example shows how to set up a key manager to perform client
 * authentication.
 *
 * This program assumes that the client is not inside a firewall.
 * The application can be modified to connect to a server outside
 * the firewall by following SSLSocketClientWithTunneling.java.
 */
public class Client {
    /*göra frågar tre frågor till användaren och returnerar svaren som en array av strings */
    public String[] göra() throws IOException{
        System.out.println("Read, Write or Delete?");
        String[] toDo = new String[3];
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        String msg = read.readLine();
        msg = msg.toLowerCase();
        while(!(msg.equals("read") || msg.equals("write") || msg.equals("delete"))){
            System.out.println("Not an option, please re-enter read, write or delete?");
            msg = read.readLine();
            msg = msg.toLowerCase(); 
        }
        toDo[0] = "read";
        System.out.println("User ID of patient:");
        msg = read.readLine();
        toDo[1] = msg;
        System.out.println("Journal ID, if new enter 'new', if all enter 'all'");
        msg = read.readLine();
        toDo[2] = msg;
        return toDo;
    }

    public static void main(String[] args) throws Exception {
        Client klas = new Client();
        String host = null;
        int port = -1;
        for (int i = 0; i < args.length; i++) {
            System.out.println("args[" + i + "] = " + args[i]);
        }
        if (args.length < 2) {
            System.out.println("USAGE: java client host port");
            System.exit(-1);
        }
        try { /* get input parameters */
            host = args[0];
            port = Integer.parseInt(args[1]);
        } catch (IllegalArgumentException e) {
            System.out.println("USAGE: java client host port");
            System.exit(-1);
        }
        
        try { /* set up a key manager for client authentication */
            SSLSocketFactory factory = null;
            try {
                BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Username:"); 
                String userName = read.readLine();
                System.out.print("Password:");
                String pw = read.readLine();
                char[] password = pw.toCharArray();
                
                KeyStore ks = KeyStore.getInstance("JKS");
                KeyStore ts = KeyStore.getInstance("JKS");
                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
                TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
                SSLContext ctx = SSLContext.getInstance("TLS");
                ks.load(new FileInputStream(userName+"keystore"), password);  // keystore password (storepass)
				ts.load(new FileInputStream("clienttruststore"), password); // truststore password (storepass);
				kmf.init(ks, password); // user password (keypass)
				tmf.init(ts); // keystore can be used as truststore here
				ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
                factory = ctx.getSocketFactory();
            } catch (Exception e) {
                throw new IOException(e.getMessage());
            }
            SSLSocket socket = (SSLSocket)factory.createSocket(host, port);
            System.out.println("\nsocket before handshake:\n" + socket + "\n");

            /*
             * send http request
             *
             * See SSLSocketClient.java for more information about why
             * there is a forced handshake here when using PrintWriters.
             */
            socket.startHandshake();

            SSLSession session = socket.getSession();
            X509Certificate cert = (X509Certificate)session.getPeerCertificateChain()[0];
            String subject = cert.getSubjectDN().getName();
            String issuer = cert.getIssuerDN().getName();
            System.out.println("certificate name (subject DN field) on certificate received from server:\n" + subject + "\n");
            System.out.println("certificate name (issuer DN field) on certificate received from server:\n" + issuer + "\n");
            System.out.println("socket after handshake:\n" + socket + "\n");
            System.out.println("secure connection established\n\n");
            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));            
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;
            String[] toDo = klas.göra();
			for (;;) {
                System.out.print(">");
                msg = read.readLine();
                if (msg.equalsIgnoreCase("quit")) {
				    break;
				}
                out.println(toDo[0]);
                out.println(toDo[1]);
                out.println(toDo[2]);
                out.flush();
            }
            in.close();
			out.close();
			read.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
