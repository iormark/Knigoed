package info.knigoed.pojo;

import java.util.Date;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Book {

	public int bookId;
	public String title;
	public String author;
	public String publisher;
	public String series;
	public String pageExtent;
	public String binding;
	public String isbn;
	public String isbnReadable;
	public int age;
	public String image;
	public String edit;
	public Date lastModified;
	public String text;

	// ==
	public String ageValue;

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

	public String getIsbnReadable() {
		return isbnReadable;
	}

	public void setIsbnReadable(String isbnReadable) {
		this.isbnReadable = isbnReadable;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAgeValue() {
		return ageValue;
	}

	public void setAgeValue(String ageValue) {
		this.ageValue = ageValue;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
