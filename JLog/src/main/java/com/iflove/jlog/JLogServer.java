package com.iflove.jlog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by system on 2018/11/26.
 */
public class JLogServer {

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5036);
            System.out.println(String.format("%s/%s - waiting for device ", InetAddress.getLocalHost().getHostAddress(), server.getLocalPort()));
            while (true) {
                try {
                    final Socket client = server.accept();
                    final InetAddress address = client.getInetAddress();
                    System.out.println(String.format("%s is connected.", address));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
                                String line;
                                while ((line = buf.readLine()) != null) {
                                    System.out.println(line);
                                }
                                client.close();
                                System.out.println(String.format("%s is disconnected.", address));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
