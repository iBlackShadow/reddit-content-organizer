package test.unit

import database.Post
import reddit.Reddit
import scala.util.Success

class RedditSuite extends UnitSpec {
  test("get post information") {
    val url = "https://www.reddit.com/r/manga/comments/b8l9h7/disc_kingdom_chapter_595/"
    val title = "[DISC] Kingdom Chapter 595"
    val subreddit = "manga"

    assertResult(Success(Post(url, title, subreddit))){   
      Reddit.getPost(url)
    }
  }
}