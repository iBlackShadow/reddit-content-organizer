package database

sealed abstract class Entity[E](val id: Int) {
  def withId(id: Int): E
}

case class Post(override val id: Int, url: String, title: String, subreddit: String, tags: Set[String]) extends Entity[Post](id) {
  def withId(id: Int): Post = {
    this.copy(id = id)
  }

  def tag(tag: String): Option[Post] = {
    if (tags(tag)) {
      None
    } else {
      Some(this.copy(tags = tags + tag))
    }
  }

  def untag(tag: String): Option[Post] = {
    if (tags(tag)) {
      Some(this.copy(tags = tags - tag))
    } else {
      None
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