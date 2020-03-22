package com.exam.marlena.domain;

public class Message {

  private String to;
  private String subject;
  private String content;

  private Message(String to, String subject, String content) {
    this.to = to;
    this.subject = subject;
    this.content = content;
  }

  public static Builder builder() {
    return new Builder();
  }

  public String getTo() {
    return to;
  }

  public String getSubject() {
    return subject;
  }

  public String getContent() {
    return content;
  }

  public static class Builder {

    private String builderTo;
    private String subject;
    private String content;

    public Builder withTo(String to) {
      this.builderTo = to;
      return this;
    }

    public Builder withSubject(String subject) {
      this.subject = subject;
      return this;
    }

    public Builder withContent(String content) {
      this.content = content;
      return this;
    }

    public Message build() {
      return new Message(builderTo, subject, content);
    }
  }
}
