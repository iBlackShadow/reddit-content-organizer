import stores.memory.Store
import database.Post

object Main extends App {
  val postStore = Store[Post]()
  for {
    (id1, store1) <- postStore.create(Post("url1", "title1"))
    (id2, store2) <- store1.create(Post("url2", "title2"))
  } yield println(store2.getEntries)
}