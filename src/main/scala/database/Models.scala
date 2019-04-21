package database

import scala.util.{ Try, Success, Failure }

sealed abstract class Entity[E](val id: Int) {
  def withId(id: Int): E
}

case class Post(override val id: Int, url: String, title: String, subreddit: String, tags: Set[String]) extends Entity[Post](id) {
  def withId(id: Int): Post = {
    this.copy(id = id)
  }

  def tag(tag: String): Try[Post] = {
    if (tags(tag)) {
      Failure(new DuplicatedTagException)
    } else {
      Success(this.copy(tags = tags + tag))
    }
  }

  def untag(tag: String): Try[Post] = {
    if (tags(tag)) {
      Success(this.copy(tags = tags - tag))
    } else {
      Failure(new NonExistentTagException)
    }
  }
}

object Post {
  def apply(id: Int, url: String, title: String, subreddit: String) = {
    new Post(id, url, title, subreddit, Set())
  }

  def apply(url: String, title: String, subreddit: String) = {
    new Post(0, url, title, subreddit, Set())
  }
}