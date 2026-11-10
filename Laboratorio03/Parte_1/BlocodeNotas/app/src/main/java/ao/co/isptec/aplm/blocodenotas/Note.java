package ao.co.isptec.aplm.blocodenotas;

import java.io.Serializable;

public class Note implements Serializable {
    private final String title;
    private final String text;

    public Note(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return title; // texto apresentado pelo ArrayAdapter em cada linha
    }
}