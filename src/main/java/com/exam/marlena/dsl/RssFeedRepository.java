package com.exam.marlena.dsl;

import com.exam.marlena.domain.RssFeed;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RssFeedRepository extends JpaRepository<RssFeed, Long> {

  List<RssFeed> findByEmail(final String email);
}