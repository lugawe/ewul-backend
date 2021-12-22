package org.ewul.model.db.notes;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.nio.charset.StandardCharsets;

@Entity
@DiscriminatorValue(MarkdownDocument.DOCUMENT_TYPE)
public class MarkdownDocument extends Document implements Document.Data<String> {

    public static final String DOCUMENT_TYPE = "markdown";

    public MarkdownDocument() {
    }

    @Override
    @Transient
    public String getContent() {
        return new String(getData(), StandardCharsets.UTF_8);
    }

    @Override
    @Transient
    public void setContent(String content) {
        setData(content.getBytes(StandardCharsets.UTF_8));
    }

}
