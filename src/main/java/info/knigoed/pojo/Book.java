package info.knigoed.pojo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeSet;

public class Book {

    public int bookId;
    public String author;
    public String title;
    public String publisher;
    public String series;
    public String isbn;
    public String pageExtent;
    public String binding;
    public Integer age;
    public String image;
    public String edit;
    public Date lastModified;
    public String description;
    public String years;


    public TreeSet<Price> prices;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getPageExtent() {
        return pageExtent;
    }

    public void setPageExtent(String pageExtent) {
        this.pageExtent = pageExtent;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public TreeSet<Price> getPrices() {
        return prices;
    }

    public void setPrices(TreeSet<Price> prices) {
        this.prices = prices;
    }

    //
    public String ageValue;
    private Isbn isbnObject = new Isbn();

    public String getAgeValue() {
        return ageValue;
    }

    public void setAgeValue(String ageValue) {
        this.ageValue = ageValue;
    }


    public class Isbn {
        private boolean empty = true;
        private String quotes;
        private ArrayList<HashMap<String, String>> readable;

        public boolean isEmpty() {
            return empty;
        }

        public void setEmpty(boolean empty) {
            this.empty = empty;
        }

        public String getQuotes() {
            return quotes;
        }

        public void setQuotes(String quotes) {
            this.quotes = quotes;
        }

        public ArrayList<HashMap<String, String>> getReadable() {
            return readable;
        }

        public void setReadable(ArrayList<HashMap<String, String>> readable) {
            this.readable = readable;
        }
    }

    public void setIsbnObject(Isbn isbnObject) {
        this.isbnObject = isbnObject;
    }

    public Isbn getIsbnObject() {
        return isbnObject;
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
