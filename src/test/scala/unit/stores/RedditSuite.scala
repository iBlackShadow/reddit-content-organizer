package test.unit.stores

import test.unit.UnitSpec
import database.Post
import reddit.Reddit

class RedditSuite extends UnitSpec {
  test("get post information") {
    val url = "https://www.reddit.com/r/manga/comments/b8l9h7/disc_kingdom_chapter_595/"
    val title = "[DISC] Kingdom Chapter 595"
    val subreddit = "manga"

    assertResult(Some(Post(url, title, subreddit))){   
      Reddit.getPost(url)
    }
  }
}