import java.io.*;
import java.util.*;
import java.net.Socket;

public class Client {
    //private static DataOutputStream dataOutputStream = null;
    //private static DataInputStream dataInputStream = null;

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        try{      
            try (Socket s = new Socket("localhost",8000); DataOutputStream dout = new DataOutputStream(s.getOutputStream())) {
                System.out.println("enter the code to create room");
                String code=sc.next();
                dout.writeUTF(code);
                dout.flush();
            }   
}catch(IOException e){System.out.println(e);} 
    }

    void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}