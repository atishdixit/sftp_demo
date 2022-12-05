package com.cb.client;

import com.cb.constant.ChannelType;
import com.jcraft.jsch.*;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.cb.constant.AppConstant.*;

public final class SftpClient {

    private final static Logger logger = Logger.getLogger(SftpClient.class.getName());
    private String server;
    private int port;
    private String login;
    private String password;
    private Session session = null;
    private Channel channel = null;
    private ChannelSftp channelSftp = null;

    public SftpClient(String server, int port, String login, String password) {
        this.server = server;
        this.port = port;
        this.login = login;
        this.password = password;
    }

    /**
     *
     */
    public synchronized void connect(ChannelType channelType) {
        JSch jsch = new JSch();
        Properties config = new Properties();

        try {
            jsch.setKnownHosts(PRIVATE_HOSTS_KEY);
            session = jsch.getSession(login, server, port);

            config.put(CONFIG_STRICT_HOST_KEY_CHECKING, CONFIG_STRICT);
            session.setConfig(config);
            jsch.addIdentity(PRIVATE_HOSTS_KEY);

            // authenticate using password
            session.setPassword(password);

            // 10 seconds session timeout
            session.connect(SESSION_TIMEOUT);

            channel = session.openChannel(channelType.getValue());

            // 5 seconds timeout
            channel.connect(CHANNEL_TIMEOUT);
        } catch (JSchException e) {
            e.printStackTrace();
            throw new RuntimeException("Connection to server is closed. Open it first.");
        }
    }

    /**
     *
     */
    public synchronized void disconnect() {
        if (channelSftp != null) {
            logger.info("Disconnecting sftp channel");
            channelSftp.disconnect();
        }
        if (channel != null) {
            logger.info("Disconnecting channel");
            channel.disconnect();
        }
        if (session != null) {
            logger.info("Disconnecting session");
            session.disconnect();
        }
    }

    /**
     *
     * @param sourceFile
     * @param result
     * @param <T>
     * @return
     */
    public synchronized <T> List<T> readFile(String sourceFile, Class<T> result){
        channelSftp = (ChannelSftp) channel;
        List<T> results = null;
        if (channelSftp == null || session == null || !session.isConnected() || !channelSftp.isConnected()) {
           logger.warning("Connection to server is closed. Open it first.");
           return null;
        }
        try {
            logger.info("Downloading file to server");
            InputStream stream = channelSftp.get(sourceFile);

            results = new CsvToBeanBuilder(new InputStreamReader(stream))
                    .withSkipLines(1)
                    .withType(result)
                    .build()
                    .parse();

            logger.info("Download successfull.");
        } catch (SftpException e) {
            logger.warning(e.getMessage());
        }
        return results;
    }

    public List<String>  files(){
        Vector<ChannelSftp.LsEntry> list = null;
        List<String> files = new ArrayList<>();
        channelSftp = (ChannelSftp) channel;
        try {
            list = channelSftp.ls("/E:/documents/*.csv");
            for(ChannelSftp.LsEntry entry : list) {
                System.out.println(entry.getFilename());
                files.add(entry.getFilename());
            }
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return files;
    }

    public String excCommand(String command){
        StringBuffer outputBuffer =  new StringBuffer();
        try {
            ((ChannelExec)channel).setCommand(command);
            InputStream commandOutput = channel.getInputStream();
            channel.connect();
            int readByte = commandOutput.read();

            while(readByte != 0xffffffff){
                outputBuffer.append((char)readByte);
                readByte = commandOutput.read();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return outputBuffer.toString();
    }
}