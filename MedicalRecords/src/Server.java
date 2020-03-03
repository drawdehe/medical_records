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
/* hämtar ut Organization (O) och Organisation Unit (OU) från Subject strängen vi får av certifikatet, kan t.ex vara O = division B och
OU = doctor*/
    public String[] getGroupPrivilege(String subject){
        String group = "";
        String privilege = "";
        String[] result = new String[2];
        for(int i = 0; i < subject.length()-2; i++){
            if(subject.charAt(i)== 'O' && subject.charAt(i+1) == 'U' && subject.charAt(i+2) == '='){
                while(subject.charAt(i+3) != ','){
                    group+= subject.charAt(i+3);
                    i++;
                }
                break;
            }
        }
        for(int i = 0; i < subject.length()-2; i++){
            if(subject.charAt(i)== 'O' && subject.charAt(i+1) == '='){
                while(subject.charAt(i+2) != ','){
                    privilege+= subject.charAt(i+2);
                    i++;
                }
                break;
            }
        }
        result[0] = group;
        result[1]= privilege;
        return result;
    }
/* tror inte denna används men den läser typ lines från en en sträng och splittar den där det är ett mellanrum, t.ex "elias password" blir
[elias, password] och sparas sen i hashmap med användar namn o lösen*/
    public HashMap read(String username) throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader("file.txt"));
        HashMap<String, String> passwords = new HashMap<String, String>();
        try {
            String line = br.readLine();

            while (line != null) {
                String[] user = line.split(" ", 0);
                passwords.put(user[0], user[1]);
                line = br.readLine();
            }
                return passwords;
            } finally {
                br.close();
            }
    }
/* öppnar filen file.tx, läser in rader, br.readline tar nästa rad i texten, läsningen avslutas när det inte finns någon line kvar eller
om man har hittat rätt username. Varje line i textfilen ser ut såhär "username group". line splittas vid mellanrum och blir till 
en array av strings  -> [username, group]. när rätt username hittats returneras group. om rätt username inte hittas returneras "-1" */
    public String getGroup(String username) throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader("file.txt"));
        try {
            String line = br.readLine();

            while (line != null) {
                String[] user = line.split(" ", 0);
                System.out.println(user[0]);
                if(user[0].equals(username)){
                    return user[1];
                }
                line = br.readLine();
            }
                return "-1";
            } finally {
                br.close();
            }
    }
/* tar subject strängen vi får från certifikatet och parsar den, hämtar ut fältet Common Name (CN = ) och returerar den*/
    public String getName(String subject){
        String name = "";
        for(int i = 0; i < subject.length()-2; i++){
            if(subject.charAt(i)== 'C' && subject.charAt(i+1) == 'N' && subject.charAt(i+2) == '=' && subject.charAt(i+3) == '"'){
                while(subject.charAt(i+4) != '"'){
                    name+= subject.charAt(i+4);
                    i++;
                }
                break;
                
                //testkommentar
            }
        }
        return name;
    }
/* isAssociated går in i en map som ska vara sparad för varje patient, om patientens namn är Alban skulle mapen heta Alban, while loopen stegar 
igenom varje line med br.readline(), om den hittar en line som är samma subjektets namn returneras true, om det aldrig hittas returneras false.
varje line i file.txt består endast av ett personnummer på en läkare eller sjuksyster som är associerad med patienten.*/
    public Boolean isAssociated(String name, String patient) throws FileNotFoundException, IOException{
        String file = patient+"/file.txt";

        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String line = br.readLine();

            while (line != null) {
                if(line.equals(name)){
                    return true;
                }
                line = br.readLine();
            }
                return false;
            } finally {
                br.close();
            }
    }
/*getPass tar in vad subjektet vill göra, subjektet som vi fått från certifikatet,patientens namn och namnet på journalen (doc).
det finns sedan olika cases fsom tar hänsyn till vad subjektet har för roll, samt de andra variablerna. funktionen använder sig av get groupprivilege
isAssociated och getName*/
    public Boolean getPass(String action, String subject, String patient, String doc) throws FileNotFoundException, IOException{
        String[] groupPrivilege = getGroupPrivilege(subject);
        String name = getName(subject);
        switch(groupPrivilege[1]){

            case "government":
            if(action.equals("delete")){
                return true;
            }
            if(action.equals("read")) {
            	return true;
            }            
            break;

            case "doctor":
            if(action.equals("read")){
                if(groupPrivilege[0].equals(getGroup(patient))){
                    return true;
                }
                return false;
            }
            if(action.equals("write")){
                if(isAssociated(name, patient)){
                    return true;
                }
                return false;
            }
            break;

            case "nurse":
            if(action.equals("read")){
                if(groupPrivilege[0].equals(getGroup(patient))){
                    return true;
                }
                return false;
            }
            if(action.equals("write") && !doc.equals("new")){
                if(isAssociated(name, patient)){
                    return true;
                }
                return false;
            }
            break;

        }
        return false;
    }

    public void run() {
        try {
            SSLSocket socket=(SSLSocket)serverSocket.accept();
            newListener();
            SSLSession session = socket.getSession();
            X509Certificate cert = (X509Certificate)session.getPeerCertificateChain()[0];
            String subject = cert.getSubjectDN().getName();
            String issuer = cert.getIssuerDN().getName();
            int serial = cert.getSerialNumber().intValue();
    	    numConnectedClients++;
            System.out.println("client connected");
            System.out.println("client name (cert subject DN field): " + subject);
            System.out.println("client name (cert issuer DN field): " + issuer);
            System.out.println(serial);    
            System.out.println(numConnectedClients + " concurrent connection(s)\n");
            HashMap läs = read("ss");
            System.out.println(läs.get("rrrrr"));
            PrintWriter out = null;
            BufferedReader in = null;
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String[] res = getGroupPrivilege(subject);
            Boolean name =isAssociated(getName(subject), "1");
            System.out.println(name);
            String clientMsg = null;
            String action = in.readLine();
            String patient = in.readLine();
            String doc = in.readLine();
            while ((clientMsg = in.readLine()) != null) {
			    String rev = new StringBuilder(clientMsg).reverse().toString();
				out.println(rev);
				out.flush();
			}
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

    private void newListener() { (new Thread(this)).start(); } // calls run()

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
            ((SSLServerSocket)ss).setNeedClientAuth(true); // enables client authentication
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

                ks.load(new FileInputStream("serverkeystore"), password);  // keystore password (storepass)
                ts.load(new FileInputStream("servertruststore"), password); // truststore password (storepass)
                kmf.init(ks, password); // certificate password (keypass)
                tmf.init(ts);  // possible to use keystore as truststore here
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
