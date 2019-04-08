import stores.memory.Store
import database.Post
import reddit.Reddit

object Main extends App {
  val postStore = Store[Post]()
  for {
    post1 <- Reddit.getPost("https://www.reddit.com/r/programming/comments/bagklh/created_an_online_tool_to_create_entity/")
    post2 <- Reddit.getPost("https://www.reddit.com/r/manga/comments/b8l9h7/disc_kingdom_chapter_595/")
    (id1, store1) <- postStore.create(post1)
    (id2, store2) <- store1.create(post2)
  } yield println(store2.getEntries)
}