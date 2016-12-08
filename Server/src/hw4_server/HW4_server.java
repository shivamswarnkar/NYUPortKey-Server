/*
Shivam Swarnkar */
package hw4_server;
import java.io.*;
import java.util.*;
import java.net.*;
/**
 *
 * @author RAMNARAYAN
 */
public class HW4_server {
    
    static ArrayList<Socket> clr =new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Clients cl = new Clients(clr);
         try{
            ServerSocket ss = new ServerSocket(5190);
            System.out.println(ss.getLocalSocketAddress());
            while(true){
                Socket sock = ss.accept();
                System.out.println(sock.getInetAddress().getHostAddress()+" connected");
                clr.add(sock);
                PrintStream ps;
                ps = new PrintStream(sock.getOutputStream());
                ps.print("Welcome!");
                
                try{
                Scanner sin = new Scanner(sock.getInputStream());
                PrintStream sout = new PrintStream(sock.getOutputStream());
                
                while (!sock.isClosed()){
                    String line = sin.nextLine();
                    System.out.println(line);
                    
                     ps = new PrintStream(sock.getOutputStream());
                    ps.print("Recived Data");
                    
                }
        }
        catch(IOException e){}
        System.out.println(sock.getInetAddress().getHostAddress()+" disconnected");
        
                new ProcessClient(sock,cl).start();
            }
        }
        catch(IOException e){
            System.out.println("Caught IOException!");
        }
    }
}
    
class Clients{
        static ArrayList<Socket> clients;
        Clients(ArrayList<Socket> curr){
            clients = curr;
        }
       
        public void write(String name,String message) throws IOException{
            PrintStream ps;
            for(Socket x:clients){
                ps = new PrintStream(x.getOutputStream());
                ps.print(name+": "+message+"\n");
            }
    }
        
}

class ProcessClient extends Thread{
    Clients cl;
    Socket sock;
    String name;
    ProcessClient(Socket newSock, Clients newCl){
        sock=newSock;
        name = "";
        cl = newCl;
    }
    
    public void run(){
        try{
            Scanner sin = new Scanner(sock.getInputStream());
            PrintStream sout = new PrintStream(sock.getOutputStream());
                
            while (!sock.isClosed()){
                String line = sin.nextLine();
                if(name.equals("")){
                //    System.out.println(sock.getInetAddress()+"has selected name: "+line);
                    name = line;
                }
                else{
                    cl.write(name,line);
                  //  System.out.println(name+": "+line);
                }
                
                if (line.equalsIgnoreCase("EXIT"))
                    sock.close();
            }
        }
        catch(IOException e){}
        System.out.println(sock.getInetAddress().getHostAddress()+" disconnected");
        
    }
}


    


