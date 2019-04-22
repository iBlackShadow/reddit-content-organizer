package test.unit

import database.Post
import reddit.{ Reddit, InvalidPostUrlException }
import scala.util.Success
import org.scalatest.TryValues._

class RedditSuite extends UnitSpec {
  test("get post information") {
    val url = "https://www.reddit.com/r/manga/comments/b8l9h7/disc_kingdom_chapter_595/"
    val title = "[DISC] Kingdom Chapter 595"
    val subreddit = "manga"

    assertResult(Success(Post(url, title, subreddit))){   
      Reddit.getPost(url)
    }
  }

  test("get post information - invalid url") {
    val url = "asd"

    assert(Reddit.getPost(url).failure.exception.isInstanceOf[InvalidPostUrlException])
  }

  test("get post non reddit url") {
    val url = "https://www.google.com"

    assert(Reddit.getPost(url).failure.exception.isInstanceOf[InvalidPostUrlException])
  }
}