package com.matuszak.engineer.domain.project.model;

import com.matuszak.engineer.domain.project.validator.HyperlinkValidator;
import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class SourceCodeHolderLink {
    private String link;

    public SourceCodeHolderLink(String link) {
        HyperlinkValidator.isValueValid(link);
        this.link = link;
    }
}
