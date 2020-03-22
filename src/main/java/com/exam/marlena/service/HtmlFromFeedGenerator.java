package com.exam.marlena.service;

import com.exam.marlena.domain.Feed;

class HtmlFromFeedGenerator {

  String generate(Feed feed) {
    return
        "<h1>" + feed.getDescription() + "</h1>" +
            feed.getEntries().stream().map(entry ->
                "<div>" +
                    "<h2><a href=\n" + entry.getUrl() + ">" + entry.getTitle() + "</a></h2>" +
                    "<p>" + entry.getDescription() + "</p></div>"
            ).reduce("", (t1, t2) -> t1 + t2);
  }
}
