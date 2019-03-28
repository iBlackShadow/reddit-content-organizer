package test.unit.stores

import test.unit.UnitSpec
import stores.memory.PostStore
import reddit.Post

class PostStoreSuite extends UnitSpec {
    test("save post assigns an id") {
        val store = PostStore()
        val post = Post("url", "title")

        store.create(post)

        assert(store.getPosts().get(0).id == 1)
    }
}