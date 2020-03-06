import java.io.*;
import java.net.*;
import java.security.KeyStore;
import javax.net.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;
import java.util.HashMap;

public class Server implements Runnable {
    private ServerSocket serverSocket = null;
    private static int numConnectedClients = 0;

    public Server(ServerSocket ss) throws IOException {
        serverSocket = ss;
        newListener();
    }

    public void run() {
        try {
            SSLSocket socket = (SSLSocket) serverSocket.accept();
            newListener();
            SSLSession session = socket.getSession();
            X509Certificate cert = (X509Certificate) session.getPeerCertificateChain()[0];

            String subject = cert.getSubjectDN().getName();
            String issuer = cert.getIssuerDN().getName();
            int serial = cert.getSerialNumber().intValue();
            numConnectedClients++;
            System.out.println("client connected");
            System.out.println("client name (cert subject DN field): " + subject);
            System.out.println("client name (cert issuer DN field): " + issuer);
            System.out.println(serial);
            System.out.println(numConnectedClients + " concurrent connection(s)\n");

            PrintWriter out = null;
            BufferedReader in = null;
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String clientMsg = null;

            //
            ///////////////////////////////////////////////
            ///////////////////////////////////////////////
            //////////// STORA ANDRINGAR
            // tar emot kommandon hÃ¤r
            String action = in.readLine(); // Command: Read/Write/Add/Delete
            String patientSSN = in.readLine(); // SSN
            String data = in.readLine();

            System.out.println(action);
            System.out.println(patientSSN);
            System.out.println(data);


            Authorization auth = new Authorization();
            String[] certInfo = new String[3];
            certInfo[0] = auth.getName(subject);
            certInfo[1] = auth.getGroupPrivilege(subject)[0];
            certInfo[2] = auth.getGroupPrivilege(subject)[1];
            

            PatientFileManager man = new PatientFileManager();
            Boolean permission = false;

            if (action.equals("read")) {
                PatientFile pf = man.readFile(patientSSN);
                if (pf.getDoctorName().equals(certInfo[0]) || pf.getNurseName().equals(certInfo[0])
                        || pf.getPatientName().equals(certInfo[0]) || pf.getDivision().equals(certInfo[1])
                        || certInfo[2].equals( "government") ){
                    permission = true;
                    out.println(pf.toString());
                    out.flush();
                }
            }

            if (action.equals("write")) {
                PatientFile pf = man.readFile(patientSSN);
                if ((certInfo[2].equals("doctor") || certInfo[2].equals("nurse"))
                        && (pf.getDoctorName().equals(certInfo[0]) || pf.getNurseName().equals(certInfo[0]))){
                    permission = true;
                    man.writeToFile(patientSSN, data);
                    out.println("Appended the text!");
                    out.flush();
                }
            }

            if (action.equals("add")){
                if (certInfo[2].equals("doctor")) {
                    permission = true;
                    String[] pInfo = data.split(":");
                    man.createFile(patientSSN,
                            new PatientFile(pInfo[0], patientSSN, certInfo[0], pInfo[3], pInfo[4], pInfo[5]));
                    out.println("Added the patient!");
                    out.flush();
                }
            }

            if (action.equals("delete")) {
                if (certInfo[2].equals("government")){
                    permission = true;
                    man.deleteFile(patientSSN);
                    out.println("Deleted the patient!");
                    out.flush();
                }
            }

            // Error/Deny Message
            Log log = new Log();
            log.newLogEntry(certInfo[0], patientSSN, action, permission);
            if (!permission) {
                out.println("Request to " + action + " has been DENIED! (or failed)");
            } else {
                out.println("Request to " + action + " has been APPROVED!");
            }
            out.flush();
            in.close();
            out.close();
            socket.close();
            numConnectedClients--;
            System.out.println("client disconnected");
            System.out.println(numConnectedClients + " concurrent connection(s)\n");
        } catch (IOException e) {
            System.out.println("Client died: " + e.getMessage());
            e.printStackTrace();
            return;
        }
    }

    private void newListener() {
        (new Thread(this)).start();
    } // calls run()

    public static void main(String args[]) {
        System.out.println("\nServer Started\n");
        int port = -1;
        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
        }
        String type = "TLS";
        try {
            ServerSocketFactory ssf = getServerSocketFactory(type);
            ServerSocket ss = ssf.createServerSocket(port);
            ((SSLServerSocket) ss).setNeedClientAuth(true); // enables client authentication
            new Server(ss);
        } catch (IOException e) {
            System.out.println("Unable to start Server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static ServerSocketFactory getServerSocketFactory(String type) {
        if (type.equals("TLS")) {
            SSLServerSocketFactory ssf = null;
            try { // set up key manager to perform server authentication
                SSLContext ctx = SSLContext.getInstance("TLS");
                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
                TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
                KeyStore ks = KeyStore.getInstance("JKS");
                KeyStore ts = KeyStore.getInstance("JKS");
                char[] password = "password".toCharArray();

                ks.load(new FileInputStream("serverkeystore"), password); // keystore password (storepass)
                ts.load(new FileInputStream("servertruststore"), password); // truststore password (storepass)
                kmf.init(ks, password); // certificate password (keypass)
                tmf.init(ts); // possible to use keystore as truststore here
                ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
                ssf = ctx.getServerSocketFactory();
                return ssf;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return ServerSocketFactory.getDefault();
        }
        return null;
    }
}