package test.unit

import org.scalatest.TryValues._
import stores.EntryNotFoundException
import stores.memory.Store
import database.Post
import scala.util.{ Success, Failure }

class PostStoreSuite extends UnitSpec {
  val store = Store[Post]()

  test("create adds an entry") {
    val post = Post("url", "title", "subreddit")

    assertResult(Success(1)){ 
      for {
        (_, store2) <- store.create(post)
        posts1 <- store.getEntries
        posts2 <- store2.getEntries
      } yield posts2.size - posts1.size
    }
  }

  test("create assigns a sequential id") {
    val post = Post("url", "title", "subreddit")

    assertResult(Success(1, 2, 3)){ 
      for {
        (id1, store2) <- store.create(post)
        (id2, store3) <- store2.create(post)
        (id3, _) <- store3.create(post)
      } yield (id1, id2, id3)
    }
  }

  test("get entries return list of entries") {
    val post1 = Post("url 1", "title 1", "subreddit 1")
    val post2 = Post("url 2", "title 2", "subreddit 2")

    assertResult(Success(List(post1.copy(id = 1), post2.copy(id = 2)))){
      for {
        (_, store2) <- store.create(post1)
        (_, store3) <- store2.create(post2)
        entries <- store3.getEntries
      } yield entries
    }
  }

  test("update changes existing entry values") {
    val post = Post("url", "title", "subreddit")
    val updatedPost = Post(1, "updated url", "updated title", "updated subreddit")

    assertResult(Success(updatedPost)){
      for {
        (_, store2) <- store.create(post)
        (store3) <- store2.update(updatedPost)
      } yield store3.getEntries.get(0)
    }
  }

  test("update fails when post has no id") {
    val post = Post("url", "title", "subreddit")

    assert(store.update(post).failure.exception.isInstanceOf[EntryNotFoundException])
  }

  test("update fails when id does not exist in the store") {
    val post = Post(1, "url", "title", "subreddit")

    assert(store.update(post).failure.exception.isInstanceOf[EntryNotFoundException])
  }
}