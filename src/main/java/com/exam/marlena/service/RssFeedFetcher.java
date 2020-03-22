package com.exam.marlena.service;

import com.exam.marlena.domain.Feed;
import com.exam.marlena.domain.FeedEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class RssFeedFetcher {

  Feed fetch(final String rssUrl) {
    URL feedSource = null;
    try {
      feedSource = new URL(rssUrl);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    SyndFeedInput input = new SyndFeedInput();

    try {
      assert feedSource != null;
      final SyndFeed feed = input.build(new XmlReader(feedSource));
      final List<SyndEntryImpl> entries = feed.getEntries();
      return Feed.builder()
          .description(feed.getDescription())
          .entries(entries.stream().map(entry -> FeedEntry.builder()
              .description(entry.getDescription().getValue())
              .url(entry.getUri())
              .title(entry.getTitle())
              .build()
          ).collect(Collectors.toList()))
          .build();

    } catch (FeedException | IOException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }
}
