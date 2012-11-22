package models;

import java.util.*;
import javax.presistent.*;

import java.db.jpa.*

@Entity
public class comment extends Model {

	public String author;
	public Date postedAt;

	@Lob
	public String content;

	@ManyToMany
	public Post post;

	public Comment(Post post, String author, String content) {
		this.post = post;
		this.author = author;
		this.content = content;
		this.postedAt = new Date();
	}

}