package com.matuszak.engineer.project.model;

import com.matuszak.engineer.project.validator.HyperlinkValidator;
import lombok.Data;

@Data
public class SourceCodeHolderLink {
    private String link;

    public SourceCodeHolderLink(String link) {
//        HyperlinkValidator.isValueValid(link);
        this.link = link;
    }
}
