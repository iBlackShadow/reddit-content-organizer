package database

sealed abstract class Entity[E](val id: Int) {
    def withId(id: Int): E
}

case class Post(override val id: Int, url: String, title: String, tags: List[String]) extends Entity[Post](id) {
    def withId(id: Int) = {
        this.copy(id = id)
    }
}

object Post {
    def apply(url: String, title: String) = {
        new Post(0, url, title, List())
    }
}