package test.unit

import database.Post

class PostSuite extends UnitSpec {
  val post = Post("url", "title", "subreddit")

  test("withId assigns an id") {
    assertResult(post.copy(id = 5)) {
      post.withId(5)
    }
  }

  test("tag adds a tag") {
    val expectedPost = post.copy(tags = Set("cats"))

    assertResult(Some(expectedPost)) {
      post.tag("cats")
    }
  }

  test("tag returns an error when the tag already exists") {
    assertResult(None) {
      for {
        taggedPost <- post.tag("cats")
        result <- taggedPost.tag("cats")
      } yield result
    }
  }

  test("untag removes a tag") {
    assertResult(Some(post)) {
      for {
        taggedPost <- post.tag("cats")
        result <- taggedPost.untag("cats")
      } yield result
    }
  }

  test("untag returns an error when the tag does no exists") {
    assertResult(None) {
      post.untag("cats")
    }
  }
}