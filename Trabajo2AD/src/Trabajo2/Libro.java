package Trabajo2;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Book")
public class Libro {
    private String id;
    private String author;
    private String title;
    private String genre;
    private double price;
    private String publishDate;
    private String description;

    public String getId() {
        return id;
    }

    @XmlAttribute(name = "id")
    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    @XmlElement(name = "Author")
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    @XmlElement(name = "Title")
    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    @XmlElement(name = "Genre")
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getPrice() {
        return price;
    }

    @XmlElement(name = "Price")
    public void setPrice(double price) {
        this.price = price;
    }

    public String getPublishDate() {
        return publishDate;
    }

    @XmlElement(name = "PublishDate")
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getDescription() {
        return description;
    }

    @XmlElement(name = "Description")
    public void setDescription(String description) {
        this.description = description;
    }
}

