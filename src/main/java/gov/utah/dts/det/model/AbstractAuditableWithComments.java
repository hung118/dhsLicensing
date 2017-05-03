package gov.utah.dts.det.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractAuditableWithComments<PK extends Serializable> extends AbstractAuditableEntity<Long> implements
    Serializable {

  @Column(name = "NOTES")
  private String notes;

  @Column(name = "COMMENTS")
  private String comments;

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }
}
