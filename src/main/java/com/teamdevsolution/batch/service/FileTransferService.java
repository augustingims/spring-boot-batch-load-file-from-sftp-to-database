package com.teamdevsolution.batch.service;

import java.util.List;

public interface FileTransferService {

    List<String> getFiles();

    boolean downloadFile(String localFilePath, String remoteFilePath);
}
