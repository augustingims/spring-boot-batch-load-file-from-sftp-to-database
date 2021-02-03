package com.teamdevsolution.batch.service.impl;

import com.jcraft.jsch.*;
import com.teamdevsolution.batch.config.ApplicationProperties;
import com.teamdevsolution.batch.service.FileTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

@Service
public class FileTransferServiceImpl implements FileTransferService {

    private Logger LOGGER = LoggerFactory.getLogger(FileTransferServiceImpl.class);

    private final ApplicationProperties applicationProperties;

    public FileTransferServiceImpl(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public List<String> getFiles() {
        ChannelSftp channelSftp = createChannelSftp();
        List<String> files = new ArrayList<>();
        try {
            channelSftp.cd(applicationProperties.getSftp().getDirRemote());
            Vector filelist = channelSftp.ls(applicationProperties.getSftp().getDirRemote());
            for(int i=0; i<filelist.size();i++){
                ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) filelist.get(i);
                if(entry.getFilename().startsWith(applicationProperties.getOutput().getFilename())){
                    files.add(entry.getFilename());
                }
            }
            return files;
        } catch (SftpException e) {
            LOGGER.error("Error list file", e);
        } finally {
            disconnectChannelSftp(channelSftp);
        }
        return files;
    }

    @Override
    public boolean downloadFile(String localFilePath, String remoteFilePath) {
        ChannelSftp channelSftp = createChannelSftp();
        try {
            if (channelSftp != null) {
                channelSftp.get(remoteFilePath, localFilePath);
                return true;
            }
        } catch(SftpException ex) {
            LOGGER.error("Error download file", ex);
        } finally {
            disconnectChannelSftp(channelSftp);
        }
        return false;
    }

    private ChannelSftp createChannelSftp() {
        try {
            JSch jSch = new JSch();
            Session session = jSch.getSession(applicationProperties.getSftp().getUsername(), applicationProperties.getSftp().getHost(), applicationProperties.getSftp().getPort());
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(applicationProperties.getSftp().getPassword());
            session.connect(applicationProperties.getSftp().getSessionTimeout());
            Channel channel = session.openChannel("sftp");
            channel.connect(applicationProperties.getSftp().getChannelTimeout());
            return (ChannelSftp) channel;
        } catch(JSchException ex) {
            LOGGER.error("Create ChannelSftp error", ex);
        }

        return null;
    }

    private void disconnectChannelSftp(ChannelSftp channelSftp) {
        try {
            if( channelSftp == null)
                return;

            if(channelSftp.isConnected())
                channelSftp.disconnect();

            if(channelSftp.getSession() != null)
                channelSftp.getSession().disconnect();

        } catch(Exception ex) {
            LOGGER.error("SFTP disconnect error", ex);
        }
    }
}
