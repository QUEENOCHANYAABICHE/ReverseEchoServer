package org.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSkt extends Thread{

    Socket stk;

    public ServerSkt(Socket st){
       stk = st;
    }

    public void run(){


        try {
            BufferedReader keyb = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader br = new BufferedReader(new InputStreamReader(stk.getInputStream()));
            PrintStream ps = null;
            ps = new PrintStream(stk.getOutputStream());
            String msg;

            do{
                msg=keyb.readLine();
                ps.println(msg);
                msg=br.readLine();
                System.out.println("FROM SERVER" + msg);
            }while(!msg.equals("dne"));
            stk.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws IOException {
            ServerSocket ss = new ServerSocket(3131);
            Socket stk;
            ServerSkt re;
            int count=1;

            do {
                stk = ss.accept();
                System.out.println("Client connected:" + count++);
                 re = new ServerSkt(stk);
                 re.start();
            }while(true);

           }
}

class Client{
    public static void main(String[] args) throws IOException {
        Socket stk = new Socket("localhost", 3131);

        BufferedReader keyb = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br = new BufferedReader(new InputStreamReader(stk.getInputStream()));
        PrintStream ps = new PrintStream(stk.getOutputStream());

        String msg;

        do{
            msg = keyb.readLine();
            ps.println(msg);
            msg= br.readLine();
            System.out.println("From Server"+msg);
        }while(!msg.equals("dne"));
        stk.close();
    }
}

