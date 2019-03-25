package stores.memory

import scala.collection.mutable.Map
import reddit.Post

class PostStore {
    private val posts: Map[Int, Post] = Map()

    def create(post: Post) : Option[Int] = {
        val id = posts.size + 1
        posts += (id -> post.copy(id = id))

        Some(id)
    }

    def update(post: Post) : Option[Unit] = {
        posts(post.id) = post

        Some(Unit)
    }

    def getPosts() : Option[List[Post]] = {
        Some(posts.values.toList)
    }
}

object PostStore {
    def apply() = {
        new PostStore()
    }
}