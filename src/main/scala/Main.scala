import stores.memory.PostStore
import reddit.Post

object Main extends App {
  val postStore = PostStore()
  postStore.create(Post("url1", "title1"))
  postStore.create(Post("url2", "title2"))
  println(postStore.getPosts)
}