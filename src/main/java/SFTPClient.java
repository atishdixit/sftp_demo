import com.cb.client.SftpClient;
import com.cb.constant.ChannelType;
import com.cb.dto.DomainDetails;
import com.cb.dto.MapToItem;
import com.jcraft.jsch.*;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static com.cb.constant.AppConstant.*;

public class SFTPClient {

    public static void main(String as[]){
        //readFileBuPassword();
        //readFileByFile();
       // readCSVFileByFile();
        //readCSVFileByLibsAndMap();

        //Read file: https://github.com/jpbriend/sftp-example/blob/master/src/main/java/com/infinit/sftp/SftpClient.java
        SftpClient sftpClient =new SftpClient(REMOTE_HOST, REMOTE_PORT, USERNAME, PASSWORD);
        sftpClient.connect(ChannelType.SFTP);
        List<DomainDetails> list = sftpClient.readFile(SOURCE_FILE, DomainDetails.class);
        System.out.println(list);
        List<String> files = sftpClient.files();
        sftpClient.disconnect();
        System.out.println(files);

        //Execute Commands: This service allows sftp connections only.
        //https://stackoverflow.com/questions/2405885/run-a-command-over-ssh-with-jsch
////        SftpClient sftpClient =new SftpClient(REMOTE_HOST, REMOTE_PORT, USERNAME, PASSWORD);
//          sftpClient.connect(ChannelType.EXEC);
//          String result = sftpClient.excCommand("mkdir TT");
//          System.out.println(result);
//        sftpClient.disconnect();
    }

    private static  void readFileByKey(){
         String REMOTE_HOST = "localhost";
         String USERNAME = "Dell";
         String PASSWORD = "";
         int REMOTE_PORT = 22;
         int SESSION_TIMEOUT = 10000;
         int CHANNEL_TIMEOUT = 5000;
        String localFile = "E:/documents/doc1.pdf";
        String remoteFile = "E:/documents/doc.pdf";
        Session jschSession = null;

        try {

            JSch jsch = new JSch();
            //jsch.setKnownHosts("/home/mkyong/.ssh/known_hosts");
            jsch.setKnownHosts("C:/Users/Dell/.ssh/id_rsa");
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            jschSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
            jschSession.setConfig(config);

            // authenticate using private key
             jsch.addIdentity("C:/Users/Dell/.ssh/id_rsa");

            // authenticate using password
            jschSession.setPassword(PASSWORD);

            // 10 seconds session timeout
            jschSession.connect(SESSION_TIMEOUT);

            Channel sftp = jschSession.openChannel("sftp");

            // 5 seconds timeout
            sftp.connect(CHANNEL_TIMEOUT);

            ChannelSftp channelSftp = (ChannelSftp) sftp;

            // transfer file from local to remote server
            //channelSftp.put(localFile, remoteFile);

            // download file from remote server to local
             channelSftp.get(remoteFile, localFile);

            channelSftp.exit();

        } catch (JSchException | SftpException e) {

            e.printStackTrace();

        } finally {
            if (jschSession != null) {
                jschSession.disconnect();
            }
        }

        System.out.println("Done");
    }

    private static  void readFileByFile(){
        String REMOTE_HOST = "192.168.29.70";
        String USERNAME = "Dell";
        String PASSWORD = "";
        int REMOTE_PORT = 22;
        int SESSION_TIMEOUT = 10000;
        int CHANNEL_TIMEOUT = 5000;

        try {
            JSch jsch = new JSch();
            Session jschSession = null;
            jsch.setKnownHosts("C:/Users/Dell/.ssh/id_rsa");
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            jschSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
            jschSession.setConfig(config);

            jsch.addIdentity("C:/Users/Dell/.ssh/id_rsa");

            // authenticate using password
            jschSession.setPassword(PASSWORD);

            // 10 seconds session timeout
            jschSession.connect(SESSION_TIMEOUT);

            Channel sftp = jschSession.openChannel("sftp");

            // 5 seconds timeout
            sftp.connect(CHANNEL_TIMEOUT);

            ChannelSftp sftpChannel = (ChannelSftp) sftp;
            System.out.println(sftpChannel.getHome());
            System.out.println(sftpChannel.getId());

            InputStream stream = sftpChannel.get("/E:/documents/doc.pdf");

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }

            } catch (IOException io) {
                System.out.println("Exception occurred during reading file from SFTP server due to " + io.getMessage());
                io.getMessage();

            } catch (Exception e) {
                System.out.println("Exception occurred during reading file from SFTP server due to " + e.getMessage());
                e.getMessage();

            }

            sftpChannel.exit();
            jschSession.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    private static  void readFileBuPassword(){
        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession("Dell", "192.168.29.70", 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword("A2601");
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            System.out.println(sftpChannel.getHome());
            System.out.println(sftpChannel.getId());

            InputStream stream = sftpChannel.get("/E:/documents/doc.pdf");

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }

            } catch (IOException io) {
                System.out.println("Exception occurred during reading file from SFTP server due to " + io.getMessage());
                io.getMessage();

            } catch (Exception e) {
                System.out.println("Exception occurred during reading file from SFTP server due to " + e.getMessage());
                e.getMessage();

            }

            sftpChannel.exit();
            session.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    private static  void readCSVFileByFile(){
        String REMOTE_HOST = "192.168.43.69";
        String USERNAME = "Dell";
        String PASSWORD = "";
        int REMOTE_PORT = 22;
        int SESSION_TIMEOUT = 10000;
        int CHANNEL_TIMEOUT = 5000;
        List<DomainDetails> inputList = null;
        MapToItem mapToItem = new MapToItem();
        try {
            JSch jsch = new JSch();
            Session jschSession = null;
            jsch.setKnownHosts("C:/Users/Dell/.ssh/id_rsa");
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            jschSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
            jschSession.setConfig(config);

            jsch.addIdentity("C:/Users/Dell/.ssh/id_rsa");

            // authenticate using password
            jschSession.setPassword(PASSWORD);

            // 10 seconds session timeout
            jschSession.connect(SESSION_TIMEOUT);
            Channel sftp = jschSession.openChannel("sftp");

            // 5 seconds timeout
            sftp.connect(CHANNEL_TIMEOUT);

            ChannelSftp sftpChannel = (ChannelSftp) sftp;
            InputStream stream = sftpChannel.get("/E:/documents/sample.csv");

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                inputList = br.lines().skip(1).map(mapToItem::apply).collect(Collectors.toList());
            } catch (Exception e) {
                System.out.println("Exception occurred during reading file from SFTP server due to " + e.getMessage());
                e.getMessage();
            }
            sftpChannel.exit();
            jschSession.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }

        inputList.stream().forEach(value->{
            System.out.println(value.getPovoID());
            System.out.println(value.getPovoTel());
            System.out.println(value.getPovoEmailAddr());
        });

    }

   //https://mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
    private static  void readCSVFileByLibs(){
        String REMOTE_HOST = "192.168.29.70";
        String USERNAME = "Dell";
        String PASSWORD = "";
        int REMOTE_PORT = 22;
        int SESSION_TIMEOUT = 10000;
        int CHANNEL_TIMEOUT = 5000;
        List<DomainDetails> inputList = null;
        MapToItem mapToItem = new MapToItem();
        try {
            JSch jsch = new JSch();
            Session jschSession = null;
            jsch.setKnownHosts("C:/Users/Dell/.ssh/id_rsa");
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            jschSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
            jschSession.setConfig(config);

            jsch.addIdentity("C:/Users/Dell/.ssh/id_rsa");

            // authenticate using password
            jschSession.setPassword(PASSWORD);

            // 10 seconds session timeout
            jschSession.connect(SESSION_TIMEOUT);
            Channel sftp = jschSession.openChannel("sftp");

            // 5 seconds timeout
            sftp.connect(CHANNEL_TIMEOUT);

            ChannelSftp sftpChannel = (ChannelSftp) sftp;
            InputStream stream = sftpChannel.get("/E:/documents/sample.csv");

            try (CSVReader reader = new CSVReader(new InputStreamReader(stream))) {
                List<String[]> r = reader.readAll();
                r.forEach(x -> System.out.println(Arrays.toString(x)));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvException e) {
                e.printStackTrace();
            }
            sftpChannel.exit();
            jschSession.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }
        inputList.stream().forEach(value->{
            System.out.println(value.getPovoID());
            System.out.println(value.getPovoTel());
            System.out.println(value.getPovoEmailAddr());
        });
    }

    //https://mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
    private static  void readCSVFileByLibsAndMap(){
        String REMOTE_HOST = "192.168.29.70";
        String USERNAME = "Dell";
        String PASSWORD = "";
        int REMOTE_PORT = 22;
        int SESSION_TIMEOUT = 10000;
        int CHANNEL_TIMEOUT = 5000;
        try {
            JSch jsch = new JSch();
            Session jschSession = null;
            jsch.setKnownHosts("C:/Users/Dell/.ssh/id_rsa");
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            jschSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
            jschSession.setConfig(config);

            jsch.addIdentity("C:/Users/Dell/.ssh/id_rsa");

            // authenticate using password
            jschSession.setPassword(PASSWORD);

            // 10 seconds session timeout
            jschSession.connect(SESSION_TIMEOUT);
            Channel sftp = jschSession.openChannel("sftp");

            // 5 seconds timeout
            sftp.connect(CHANNEL_TIMEOUT);

            ChannelSftp sftpChannel = (ChannelSftp) sftp;
            InputStream stream = sftpChannel.get("/E:/documents/sample.csv");
            List<DomainDetails> domainDetails = new CsvToBeanBuilder(new InputStreamReader(stream))
                    .withSkipLines(1)
                    .withType(DomainDetails.class)
                    .build()
                    .parse();
            sftpChannel.exit();
            jschSession.disconnect();
            domainDetails.forEach(System.out::println);
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }
}
