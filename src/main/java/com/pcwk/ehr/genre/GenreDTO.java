package com.pcwk.ehr.genre;

import com.pcwk.ehr.cmn.DTO;

public class GenreDTO extends DTO {
	private int bookgenre; //장르번호
	private String genreName; //장르이름
	
	public GenreDTO() {}

	public GenreDTO(int bookgenre, String genreName) {
		super();
		this.bookgenre = bookgenre;
		this.genreName = genreName;
	}

	public int getBookgenre() {
		return bookgenre;
	}

	public void setBookgenre(int bookgenre) {
		this.bookgenre = bookgenre;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	@Override
	public String toString() {
		return "GenreDTO [bookgenre=" + bookgenre + ", genreName=" + genreName + ", toString()=" + super.toString()
				+ "]";
	}
	
}
