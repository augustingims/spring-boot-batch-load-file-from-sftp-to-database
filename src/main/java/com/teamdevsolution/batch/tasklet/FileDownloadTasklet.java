package com.teamdevsolution.batch.tasklet;

import com.teamdevsolution.batch.config.ApplicationProperties;
import com.teamdevsolution.batch.service.FileTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.List;

public class FileDownloadTasklet implements Tasklet {

    private Logger LOGGER = LoggerFactory.getLogger(FileDownloadTasklet.class);

    private final ApplicationProperties applicationProperties;

    private final FileTransferService fileTransferService;

    public FileDownloadTasklet(ApplicationProperties applicationProperties, FileTransferService fileTransferService) {
        this.applicationProperties = applicationProperties;
        this.fileTransferService = fileTransferService;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        List<String> lists = fileTransferService.getFiles();

        lists.forEach(file -> {
            String localPath = String.format("%s/%s", applicationProperties.getOutput().getPath(), file);
            String remotePath = String.format("%s/%s", applicationProperties.getSftp().getDirRemote(), file);
            fileTransferService.downloadFile(localPath,remotePath);
        });

        return RepeatStatus.FINISHED;
    }
}
