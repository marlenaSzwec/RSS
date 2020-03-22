package com.exam.marlena.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;


@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RssFeed {

  @Id
  @GeneratedValue
  private Long id;
  private String email;
  private String rss;

  @Type(type = "text")
  @Column(name = "content", length = 65535, columnDefinition = "TEXT")
  private String content;
  private Boolean isActive;

  public void setId(Long id) {
    this.id = id;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setRss(String rss) {
    this.rss = rss;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public Long getId() {
    return this.id;
  }

  public String getEmail() {
    return this.email;
  }

  public String getRss() {
    return this.rss;
  }

  public String getContent() {
    return this.content;
  }

  public Boolean getIsActive() {
    return this.isActive;
  }
}
