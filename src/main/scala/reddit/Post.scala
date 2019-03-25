package reddit

case class Post(id: Int, url: String, title: String, tags: List[String])

object Post {
    def apply(url: String, title: String) = {
        new Post(0, url, title, List())
    }
}