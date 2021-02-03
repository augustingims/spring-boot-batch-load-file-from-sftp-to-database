package com.teamdevsolution.batch.writers;

import com.teamdevsolution.batch.domain.Formateur;
import com.teamdevsolution.batch.repository.FormateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class FormateurWriter implements ItemWriter<Formateur> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormateurWriter.class);

    private final FormateurRepository formateurRepository;

    public FormateurWriter(FormateurRepository formateurRepository) {
        this.formateurRepository = formateurRepository;
    }

    @Override
    public void write(List<? extends Formateur> list) throws Exception {
        LOGGER.debug("Start of writing");
        formateurRepository.saveAll(list);
    }
}
