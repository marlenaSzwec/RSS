package com.exam.marlena.controller;

import com.exam.marlena.domain.RssFeed;
import com.exam.marlena.service.RssFeedService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RssFeedController {

  private final RssFeedService feedService;

  @Autowired
  public RssFeedController(final RssFeedService feedService) {
    this.feedService = feedService;
  }

  @PostMapping(value = "/rssFeed")
  public String saveFeed(final RssFeed rssFeed, final Model model) {
    feedService.saveFeed(rssFeed.getEmail(), rssFeed.getRss());
    final List<RssFeed> feeds = feedService.findAll(rssFeed.getEmail());
    model.addAttribute("feeds", feeds);
    return "rssFeed";
  }

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("rssFeed", new RssFeed());
    return "rssFeed";
  }

  @GetMapping("/rssFeed")
  public String greetingForm(Model model) {
    model.addAttribute("rssFeed", new RssFeed());
    return "rssFeed";
  }

  @GetMapping(value = "/rssFeed/send")
  public ModelAndView sendMail(@RequestParam(name = "email") final String email) {
    ModelAndView mav = new ModelAndView("rssFeed");
    mav.addObject("rssFeed", RssFeed.builder().email(email).build());
    mav.addObject("preview", feedService.send(email));
    return mav;
  }

  @PostMapping("/rssFeed/{id}")
  public String delete(
      @PathVariable("id") long id, @RequestParam(name = "email") String email,
      final Model model) {
    feedService.delete(id);
    final List<RssFeed> feeds = feedService.findAll(email);
    model.addAttribute("feeds", feeds);
    model.addAttribute("rssFeed", RssFeed.builder().email(email).build());
    return "rssFeed";
  }
}


