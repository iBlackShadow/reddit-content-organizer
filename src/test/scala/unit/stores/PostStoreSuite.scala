package test.unit.stores

import test.unit.UnitSpec
import stores.memory.Store
import database.Post

class PostStoreSuite extends UnitSpec {
    test("create post assigns an id") {
        val store = Store[Post]()
        val post = Post("url", "title")

        assertResult(Some(1)){ 
            for {
                (id, store1) <- store.create(post)
                posts <- store1.getEntries
            } yield posts(0).id
        }
    }
}