package com.teamdevsolution.batch.tasklet;

import com.teamdevsolution.batch.config.ApplicationProperties;

import com.teamdevsolution.batch.utils.ConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileHistoTasklet implements Tasklet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileHistoTasklet.class);

    private final ApplicationProperties applicationProperties;


    public FileHistoTasklet(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }


    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        final File folder = new File(applicationProperties.getInput().getPath());
        if(folder.listFiles()!=null){
            List<File> files = Arrays.asList(Objects.requireNonNull(folder.listFiles()));
            LOGGER.info("File size : {}", files.size());
            for (File file: files){
                LOGGER.info("File Path : {}", file.getPath());
                LOGGER.info("Start histo file : {}", file.getName());
                String remoteHisto = String.format("%s/%s%s", applicationProperties.getOutput().getPathHisto(),ConstantUtils.CODE_START_FILE_HISTO,file.getName());
                LOGGER.info("Path histo file : {}", remoteHisto);
                boolean isUploaded = file.renameTo(new File(remoteHisto));
                LOGGER.info("Move file result: " + String.valueOf(isUploaded));
            }
        }
        return RepeatStatus.FINISHED;
    }
}
