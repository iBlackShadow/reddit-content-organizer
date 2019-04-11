package database

sealed abstract class Entity[E](val id: Int) {
  def withId(id: Int): E
}

case class Post(override val id: Int, url: String, title: String, subreddit: String, tags: List[String]) extends Entity[Post](id) {
  def withId(id: Int) = {
    this.copy(id = id)
  }
}

object Post {
  def apply(id: Int, url: String, title: String, subreddit: String) = {
    new Post(id, url, title, subreddit, List())
  }

  def apply(url: String, title: String, subreddit: String) = {
    new Post(0, url, title, subreddit, List())
  }
}