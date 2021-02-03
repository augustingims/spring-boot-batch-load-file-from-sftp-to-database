package com.teamdevsolution.batch.readers;

import com.teamdevsolution.batch.config.ApplicationProperties;
import com.teamdevsolution.batch.domain.Formateur;
import com.teamdevsolution.batch.mappers.FormateurMapper;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceArrayPropertyEditor;

public class FileDownloadReader extends MultiResourceItemReader<Formateur> {

    private final ApplicationProperties applicationProperties;

    public FileDownloadReader(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        ResourceArrayPropertyEditor resourceLoader = new ResourceArrayPropertyEditor();
        String pathRegex = String.format("%s%s%s%s", "file:", applicationProperties.getInput().getPath(), (!applicationProperties.getInput().getPath().endsWith("/") ? "/" : ""), "*");
        resourceLoader.setAsText(pathRegex);
        this.setResources((Resource[]) resourceLoader.getValue());
        this.setDelegate(loadItemFormateur());
    }

    private FlatFileItemReader<Formateur> loadItemFormateur(){
        FlatFileItemReader<Formateur> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(applicationProperties.getInput().getLinesToSkip());
        reader.setLineMapper(new FormateurMapper());
        return reader;
    }
}
