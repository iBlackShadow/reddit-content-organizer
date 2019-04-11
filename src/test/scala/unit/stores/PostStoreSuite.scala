package test.unit.stores

import test.unit.UnitSpec
import stores.memory.Store
import database.Post

class PostStoreSuite extends UnitSpec {
  test("create adds an entry") {
    val store1 = Store[Post]()
    val post = Post("url", "title", "subreddit")

    assertResult(Some(1)){ 
      for {
        (_, store2) <- store1.create(post)
        posts1 <- store1.getEntries
        posts2 <- store2.getEntries
      } yield posts2.size - posts1.size
    }
  }

  test("create assigns a sequential id") {
    val store1 = Store[Post]()
    val post = Post("url", "title", "subreddit")

    assertResult(Some(1, 2, 3)){ 
      for {
        (id1, store2) <- store1.create(post)
        (id2, store3) <- store2.create(post)
        (id3, _) <- store3.create(post)
      } yield (id1, id2, id3)
    }
  }

  test("get entries return list of entries") {
    val store1 = Store[Post]()
    val post1 = Post("url 1", "title 1", "subreddit 1")
    val post2 = Post("url 2", "title 2", "subreddit 2")

    assertResult(Some(List(post1.copy(id = 1), post2.copy(id = 2)))){
      for {
        (_, store2) <- store1.create(post1)
        (_, store3) <- store2.create(post2)
        entries <- store3.getEntries
      } yield entries
    }
  }

  test("update changes existing entry values") {
    val store1 = Store[Post]()
    val post = Post("url", "title", "subreddit")
    val updatedPost = Post(1, "updated url", "updated title", "updated subreddit")

    assertResult(Some(updatedPost)){
      for {
        (_, store2) <- store1.create(post)
        (store3) <- store2.update(updatedPost)
      } yield store3.getEntries.get(0)
    }
  }

  test("update fails when post has no id") {
    val store1 = Store[Post]()
    val post = Post("url", "title", "subreddit")
    val updatedPost = Post("updated url", "updated title", "updated subreddit")

    assertResult(None){
      for {
        (_, store2) <- store1.create(post)
        (store3) <- store2.update(updatedPost)
      } yield store3.getEntries.get(0)
    }
  }

  test("update fails when id does not exist in the store") {
    val store1 = Store[Post]()
    val updatedPost = Post(1, "updated url", "updated title", "updated subreddit")

    assertResult(None){
      for {
        (store2) <- store1.update(updatedPost)
      } yield store2.getEntries.get(0)
    }
  }
}