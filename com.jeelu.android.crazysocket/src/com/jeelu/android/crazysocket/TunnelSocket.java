package com.jeelu.android.crazysocket;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jeelu.android.Net.Common;
import android.util.Log;

public class TunnelSocket extends Thread {

        // ��������׽���
        public ServerSocket srvTunnelSocket;
        public Boolean isRuning = false;

        // ��������״̬���Ա��´β���
        private static Hashtable<String, String> connReq = new Hashtable<String, String>();

        // �̳߳�
        private ExecutorService tunnelPool = Executors.newCachedThreadPool();
        
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
                
                @Override
                public void run() {

                        //      10�����һ��HASH��
                        Log.d(Common.TAG, "clear hash table");
                        connReq.clear();
                }
        };

        /**
         * ���캯��
         */
        public TunnelSocket(ServerSocket srvSkt) {

                this.srvTunnelSocket = srvSkt;
                isRuning = true;

                timer.schedule(timerTask, 10000, 10000);
        }

        @Override
        public void run() {

                String dstHost;
                int srcPort;

                while (isRuning) {

                        try {

                                // �ȴ��ͻ�������..
                                Socket clientSocket = srvTunnelSocket.accept();

                                Log.d(Common.TAG, "Client Accept...");

                                clientSocket.setSoTimeout(120 * 1000); // ������ʱ120S

                                // �������Ķ˿ڣ������ڱ��в���
                                srcPort = clientSocket.getPort();

                                Log.d(Common.TAG, "Find Remote Address...");

                                // ����ԭʼ��Ŀ��IP��ַ
                                dstHost = getTarget(Integer.toString(srcPort).trim());

                                if (dstHost == null || dstHost.trim().equals("")) {

                                        // û���ҵ�
                                        Log.e(Common.TAG, "SPT:" + srcPort + " doesn't match");
                                        clientSocket.close();
                                        continue;
                                } else {

                                        Log.d(Common.TAG, srcPort + "-------->" + dstHost);

                                        //
                                        tunnelPool.execute(new ConnectSession(clientSocket, dstHost));
                                }

                        } catch (IOException e) {

                                Log.e(Common.TAG, "Tunnel Socket Error", e);
                        }
                }
        }

        /**
         * ����Դ�˿ںţ���dmesg�ҳ�iptables��¼��Ŀ�ĵ�ַ
         * 
         * @param sourcePort
         *            ����Դ�˿ں�
         * @return Ŀ�ĵ�ַ����ʽΪ addr:port
         */
        private synchronized String getTarget(String sourcePort) {
                String result = "";

                // �ڱ��в�����ƥ����Ŀ
                if (connReq.containsKey(sourcePort)) {

                        result = connReq.get(sourcePort);
                        connReq.remove(sourcePort);

                        Log.w(Common.TAG, "find target in cache key:" + sourcePort
                                        + " value:" + result);

                        return result;
                }

                try {
        //              BufferedReader outR = Common.Rundmesg("dmesg", "-c");
                        ArrayList<String> strReader = new ArrayList<String>();
                        Getdmesg(strReader);

        //              if (outR == null)
        //                      return null;

                        String line = "";
                        // �������������Դ�˿�Ϊkey�ĵ�ַ��
                        //while ((line = outR.readLine()) != null) {
                        for (int i = 0; i < strReader.size(); i++) {
                                
                                line = strReader.get(i);                        

                                if (line.equals(""))
                                        break;

                                boolean match = false;

                                Log.d("logs", line);

                                if (line.contains("fuckCM")) {

                                        // Log.d(Common.TAG, line);

                                        String addr = "", destPort = "", srcPort = "";
                                        String[] parmArr = line.split(" ");
                                        for (String parm : parmArr) {
                                                String trimParm = parm.trim();
                                                if (trimParm.startsWith("DST")) {
                                                        addr = getValue(trimParm);
                                                }

                                                if (trimParm.startsWith("SPT")) {

                                                        srcPort = getValue(trimParm);
                                                        if (sourcePort.equals(srcPort))
                                                                match = true;
                                                }

                                                if (trimParm.startsWith("DPT")) {
                                                        destPort = getValue(trimParm);
                                                }

                                        }

                                        if (match) {

                                                result = addr + ":" + destPort;

                                                if (connReq.containsKey(sourcePort)) {
                                                        connReq.remove(sourcePort);
                                                }

                                        } else {

                                                if (addr.length() > 0 && destPort.length() > 0
                                                                && srcPort.length() > 0) {

                                                        String strAddr = addr + ":" + destPort;

                                                        if (!connReq.contains(srcPort)) {

                                                                connReq.put(srcPort, strAddr);

                                                                Log.w(Common.TAG, "put in cache key:" + srcPort
                                                                                + " value:" + strAddr);

                                                        }
                                                }
                                        }

                                } // end if
                        } // end for

                } catch (Exception e) {
                        Log.e(Common.TAG, "Failed to exec command", e);
                }
                return result;
        }

        private String getValue(String org) {

                String result = "";
                try {
                        result = org.substring(org.indexOf("=") + 1);
                } catch (Exception e) {
                        Log.e(Common.TAG, "function getValue error", e);
                }
                return result;
        }

        public void CloseAll() {

                try {
                        isRuning = false;
                        tunnelPool.shutdownNow();

                        if (!srvTunnelSocket.isClosed())
                                srvTunnelSocket.close();

                } catch (Exception e) {
                        Log.e(Common.TAG, "function CloseAll error", e);
                }
        }

        public static synchronized int Getdmesg(ArrayList<String> strReader) {
                int result = -1;
                ArrayList<String> strError = new ArrayList<String>();
                try {

                        Process process = Runtime.getRuntime().exec("su");
                        OutputStreamWriter writer = new OutputStreamWriter(process.getOutputStream());
                        writer.write("dmesg -c\n");
                        writer.flush();
                        writer.write("exit \n");
                        writer.flush();

                        result = Common.doWaitFor(process, strReader, strError, "fuckCM");
                        if (result == 0) {
                                Log.d(Common.TAG, "dmesg exec success");
                        } else {
                                Log.d(Common.TAG, "dmesg exec with result " + result);
                        }

                        process.destroy();
                } catch (InterruptedException ie) {
                        Log.e(Common.TAG, "root command error", ie);
                } catch (Exception e) {
                        Log.e(Common.TAG, "root command error", e);
                }

                return result;
        }
}
