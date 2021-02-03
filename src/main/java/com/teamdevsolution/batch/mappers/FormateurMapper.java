package com.teamdevsolution.batch.mappers;

import com.teamdevsolution.batch.domain.Formateur;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.validation.BindException;

public class FormateurMapper extends DefaultLineMapper<Formateur> {

    public FormateurMapper() {
        this.setFieldSetMapper(formateurFieldSetMapper());
        this.setLineTokenizer(formateurTokenizer());
    }

    @Override
    public Formateur mapLine(String line, int lineNumber) throws Exception {
        return super.mapLine(line, lineNumber);
    }

    private FixedLengthTokenizer formateurTokenizer(){
        FixedLengthTokenizer fixedLengthTokenizer = new FixedLengthTokenizer();
        fixedLengthTokenizer.setStrict(false);
        fixedLengthTokenizer.setNames("id", "nom", "prenom", "adresseEmail");
        fixedLengthTokenizer.setColumns(new Range(1,5),new Range(6,21), new Range(22,37), new Range(38,63));
        return fixedLengthTokenizer;
    }

    private FieldSetMapper<Formateur> formateurFieldSetMapper(){
        return new FieldSetMapper<Formateur>() {
            @Override
            public Formateur mapFieldSet(FieldSet fieldSet) throws BindException {
                return new Formateur(fieldSet.readInt(0),fieldSet.readString(1),fieldSet.readString(2),fieldSet.readString(3));
            }
        };
    }
}
