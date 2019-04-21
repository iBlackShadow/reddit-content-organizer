package test.unit

import org.scalatest.TryValues._
import scala.util.{ Success, Failure }
import database.{ Post, DuplicatedTagException, NonExistentTagException }

class PostSuite extends UnitSpec {
  val post = Post("url", "title", "subreddit")

  test("withId assigns an id") {
    assertResult(post.copy(id = 5)) {
      post.withId(5)
    }
  }

  test("tag adds a tag") {
    val expectedPost = post.copy(tags = Set("cats"))

    assertResult(Success(expectedPost)) {
      post.tag("cats")
    }
  }

  test("tag returns an error when the tag already exists") {
    assert {
      val result = for {
        taggedPost <- post.tag("cats")
        result <- taggedPost.tag("cats")
      } yield result

      result.failure.exception.isInstanceOf[DuplicatedTagException]
    }
  }

  test("untag removes a tag") {
    assertResult(Success(post)) {
      for {
        taggedPost <- post.tag("cats")
        result <- taggedPost.untag("cats")
      } yield result
    }
  }

  test("untag returns an error when the tag does no exists") {
    assert(post.untag("cats").failure.exception.isInstanceOf[NonExistentTagException])
  }
}