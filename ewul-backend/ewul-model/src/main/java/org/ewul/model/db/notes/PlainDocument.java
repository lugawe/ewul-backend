package org.ewul.model.db.notes;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.nio.charset.StandardCharsets;

@Entity
@DiscriminatorValue("plain")
public class PlainDocument extends Document implements Document.DocumentData<String> {

    public PlainDocument() {
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
