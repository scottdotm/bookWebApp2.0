package edu.wctc.ssm.bookwebapp.model;

import java.util.Date;

/**
 * Author Object
 * @author Scott
 */
public class Author {

    private int authorId;
    private String authorName;
    private Date dateAdded;

    public Author() {
    }

    public Author(int authorId) {
        this.authorId = authorId;
    }

    public Author(int authorId, String authorName, Date dateAdded) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.dateAdded = dateAdded;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return "Author : " + " ID :" + authorId + "  Name :" + authorName + "  Timestamp :" + dateAdded + "\n\n";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.authorId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (this.authorId != other.authorId) {
            return false;
        }
        return true;
    }
}
