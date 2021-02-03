package com.teamdevsolution.batch.config;

import com.teamdevsolution.batch.domain.Formateur;
import com.teamdevsolution.batch.readers.FileDownloadReader;
import com.teamdevsolution.batch.repository.FormateurRepository;
import com.teamdevsolution.batch.service.FileTransferService;
import com.teamdevsolution.batch.tasklet.FileDownloadTasklet;
import com.teamdevsolution.batch.tasklet.FileHistoTasklet;
import com.teamdevsolution.batch.writers.FormateurWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final ApplicationProperties applicationProperties;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ApplicationProperties applicationProperties) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.applicationProperties = applicationProperties;
    }

    @Bean
    public FileDownloadTasklet fileDownloadTasklet(final ApplicationProperties applicationProperties, final FileTransferService fileTransferService){
        return new FileDownloadTasklet(applicationProperties,fileTransferService);
    }

    @Bean
    public FileDownloadReader fileDownloadReader(final ApplicationProperties applicationProperties){
        return new FileDownloadReader(applicationProperties);
    }

    @Bean
    public FormateurWriter formateurWriter(final FormateurRepository formateurRepository){
        return new FormateurWriter(formateurRepository);
    }

    @Bean
    public FileHistoTasklet fileHistoTasklet(final ApplicationProperties applicationProperties){
        return new FileHistoTasklet(applicationProperties);
    }

    @Bean
    public Step stepDownloadFile(){
        return stepBuilderFactory
                .get("stepDownloadFile")
                .tasklet(fileDownloadTasklet(null,null))
                .build();
    }

    @Bean
    public Step setpReaderFileDownlload(){
        return stepBuilderFactory
                .get("setpReaderFileDownlload")
                .<Formateur,Formateur>chunk(applicationProperties.getInput().getChunkSize())
                .reader(fileDownloadReader(null))
                .writer(formateurWriter(null))
                .build();
    }

    @Bean
    public Step stepFileHisto(){
        return stepBuilderFactory
                .get("stepFileHisto")
                .tasklet(fileHistoTasklet(null))
                .build();
    }

    @Bean
    public Job jobImportData(){
        return jobBuilderFactory
                .get("jobImportData")
                .incrementer(new RunIdIncrementer())
                .start(stepDownloadFile())
                .next(setpReaderFileDownlload())
                .next(stepFileHisto())
                .build();
    }
}
