package com.gen.desafio.api.domain.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import com.google.gson.GsonBuilder;



/**
 * DATA OBJECT - ENTITY MODEL BEAN POJO
 */
@Entity
@Table(name="posts")
public class Post implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotNull
	@Column(name = "posted_text", nullable = false, length = 250)
    private String text;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.NONE)
	@Column(name = "posted_date", nullable = false) // columnDefinition="DATETIME" -> @Temporal(TemporalType.TIMESTAMP)
    private Date date;
	
	@Column(name = "posted_image_1", nullable = true, length = 200)
    private String imageOne;
	
	@Column(name = "posted_image_2", nullable = true, length = 200)
    private String imageTwo;
	
	@Column(name = "posted_image_3", nullable = true, length = 200)
    private String imageThree;
	
	@Column(name = "posted_image_4", nullable = true, length = 200)
    private String imageFour;
	
	@Column(name = "posted_image_5", nullable = true, length = 200)
    private String imageFive;
	
	@Column(name = "posted_image_6", nullable = true, length = 200)
    private String imageSix;
	
	@Column(name = "posted_image_7", nullable = true, length = 200)
    private String imageSeven;
	
	@Column(name = "posted_image_8", nullable = true, length = 200)
    private String imageEight;
	
	@NotNull
	@Column(name = "is_comment", nullable = false)
    private boolean isComment;
	
	@NotNull
	@Column(name = "notified_on_timeline", nullable = false)
    private boolean isNotified;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
 	private User relatedUser;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentPost", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Post> comments = new ArrayList<Post>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "relatedPost", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Like> relatedLikes = new ArrayList<Like>();
    
    @ManyToOne
    @JoinColumn(name = "parent_post_id", nullable=true)
 	private Post parentPost;
    
    @ManyToMany
    @JoinTable(name = "posts_has_sectors", 
               joinColumns = { @JoinColumn(name = "post_id", nullable = false, updatable = false) }, 
               inverseJoinColumns = { @JoinColumn(name = "sector_id", nullable = false, updatable = false) })
    @Fetch(value = FetchMode.JOIN)
    private List<Sector> relatedSectors = new ArrayList<Sector>();

    

	/* Default Constructor */
	public Post(){ }
	
	public Post(String text){
		this.text = text;
	}
	
	
	/* Getters & Setters */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Post> getComments() {
		return comments;
	}

	public void setComments(List<Post> comments) {
		this.comments = comments;
	}

	public List<Like> getRelatedLikes() {
		return relatedLikes;
	}

	public void setRelatedLikes(List<Like> relatedLikes) {
		this.relatedLikes = relatedLikes;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date postedDate) {
		this.date = postedDate;
	}

	
	public User getRelatedUser() {
		return relatedUser;
	}

	public void setRelatedUser(User relatedUser) {
		this.relatedUser = relatedUser;
	}

	public Post getParentPost() {
		return parentPost;
	}

	public void setParentPost(Post parentPost) {
		this.parentPost = parentPost;
	}
	
	
	public boolean getIsComment() {
		return isComment;
	}

	public void setIsComment(boolean isComment) {
		this.isComment = isComment;
	}
	
	public String getImageOne() {
		return imageOne;
	}

	public void setImageOne(String imageOne) {
		this.imageOne = imageOne;
	}

	public String getImageTwo() {
		return imageTwo;
	}

	public void setImageTwo(String imageTwo) {
		this.imageTwo = imageTwo;
	}

	public String getImageThree() {
		return imageThree;
	}

	public void setImageThree(String imageThree) {
		this.imageThree = imageThree;
	}

	public String getImageFour() {
		return imageFour;
	}

	public void setImageFour(String imageFour) {
		this.imageFour = imageFour;
	}

	public String getImageFive() {
		return imageFive;
	}

	public void setImageFive(String imageFive) {
		this.imageFive = imageFive;
	}

	public String getImageSix() {
		return imageSix;
	}

	public void setImageSix(String imageSix) {
		this.imageSix = imageSix;
	}

	public String getImageSeven() {
		return imageSeven;
	}

	public void setImageSeven(String imageSeven) {
		this.imageSeven = imageSeven;
	}

	public String getImageEight() {
		return imageEight;
	}

	public void setImageEight(String imageEight) {
		this.imageEight = imageEight;
	}

	
	public boolean isNotified() {
		return isNotified;
	}

	public void setNotified(boolean isNotified) {
		this.isNotified = isNotified;
	}
	
	public List<Sector> getRelatedSectors() {
		return relatedSectors;
	}

	public void setRelatedSectors(List<Sector> relatedSectors) {
		this.relatedSectors = relatedSectors;
	}

	
	/*******************
     *  BEAN METHODS   *
     *******************/
			
	
	public String toGson(){
		String gson = new GsonBuilder().setPrettyPrinting().create().toJson(this);
		return gson.toString();
	}
	
//	@Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + (int) (id ^ (id >>> 32));
//        result = prime * result + ((id == null) ? 0 : id.hashCode());
//        return result;
//    }
// 
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (!(obj instanceof Post))
//            return false;
//        
//        Post other = (Post) obj;
//        if (id != other.id)
//        	return false;
//
//        return true;
//    }
	
	
}
