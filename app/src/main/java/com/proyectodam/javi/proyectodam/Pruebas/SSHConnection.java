package com.proyectodam.javi.proyectodam.Pruebas;

import android.os.AsyncTask;
import android.util.Log;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.io.InputStream;

public class SSHConnection extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... voids) {
        String result = "";
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession("pi", "192.168.50.10", 22);
            session.setPassword("raspberry");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(30000);
            executeCommand(session, "ls -la");

    }catch (JSchException e){
            result = "Error JSchException: "+e;
        }
        return result;
    }

    private static String executeCommand(Session session, String command) {

        String host = session.getHost();
        String resultado = "";

        try {
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            InputStream output = channel.getInputStream();
            channel.connect();


            if (channel.isClosed()){
                System.out.println("exit-status: " + channel.getExitStatus());
            }
            if (channel.isConnected()){
                System.out.println("Abierto: " + channel.getExitStatus());
            }
            byte[] tmp = new byte[1024];

            while (true) {
                while (output.available() > 0) {
                    int i = output.read(tmp, 0, 1024);
                    if (i < 0) {
                        System.out.print(new String(tmp, 0, i));
                        break;
                    }

                    resultado = resultado + (new String(tmp, 0, i) + "\n");
                    channel.disconnect();
                }
                return resultado;
            }

        } catch (JSchException e) {
            e.printStackTrace();
            return "ERROR JSCH";
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR IO";
        }
    }
}
