package com.exam.marlena.service;

import com.exam.marlena.domain.Feed;
import com.exam.marlena.domain.Message;
import com.exam.marlena.domain.RssFeed;
import com.exam.marlena.dsl.RssFeedRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RssFeedService {

  private final RssFeedRepository rssFeedRepository;
  private final MailSenderService mailSenderService;
  private final RssFeedFetcher rssFeedFetcher;
  private final HtmlFromFeedGenerator htmlFromFeedGenerator;

  @Autowired
  public RssFeedService(
      final RssFeedRepository rssFeedRepository,
      final MailSenderService mailSenderService,
      final RssFeedFetcher rssFeedFetcher) {
    this.rssFeedRepository = rssFeedRepository;
    this.mailSenderService = mailSenderService;
    this.rssFeedFetcher = rssFeedFetcher;
    this.htmlFromFeedGenerator = new HtmlFromFeedGenerator();
  }

  @Transactional
  public RssFeed saveFeed(final String email, final String rss) {
    final Feed feed = rssFeedFetcher.fetch(rss);
    final String html = htmlFromFeedGenerator.generate(feed);

    return rssFeedRepository.save(RssFeed.builder().rss(rss).email(email).content(html)
        .isActive(true).build());
  }

  public List<RssFeed> findAll(final String email) {
    return rssFeedRepository.findByEmail(email);
  }

  public String send(final String email) {
    final String feedAsHtml = rssFeedRepository.findByEmail(email).stream()
        .filter(RssFeed::getIsActive).map(RssFeed::getContent)
        .reduce("\n", (f1, f2) -> f1 + f2);
    final Message message = Message.builder()
        .withContent(feedAsHtml)
        .withSubject("This is your feeds...")
        .withTo(email)
        .build();
    mailSenderService.send(message);
    return feedAsHtml;
  }

  @Transactional
  public void delete(final long id) {
    rssFeedRepository.deleteById(id);
  }
}
