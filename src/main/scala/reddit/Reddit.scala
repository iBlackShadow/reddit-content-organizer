package reddit

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import database.Post
import scala.util.{ Try, Success, Failure }

object Reddit {
  def getPost(url: String): Try[Post] = {
    val result = for {
      document <- Try(new JsoupBrowser().get(url))
      title <- Try(document >> text(".Post h1"))
      post <- {
        val pattern = ".*/r/(.+?)/.*".r
        url match {
          case pattern(subreddit) => Success(Post(url, title, subreddit))
          case _ => Failure(new SubredditNotFoundException)
        }
      }
    } yield post

    result match {
      case Success(_) => result
      case Failure(exception: java.util.NoSuchElementException) => Failure(new InvalidPostUrlException(s"Title no found: ${url}"))
      case Failure(exception: java.lang.IllegalArgumentException) => Failure(new InvalidPostUrlException(s"Invalid url: ${url}"))
      case _ => result
    }
  }
}