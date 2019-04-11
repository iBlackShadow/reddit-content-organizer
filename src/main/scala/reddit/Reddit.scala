package reddit

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import database.Post

object Reddit {
  // TODO: handle invalid url
  def getPost(url: String): Option[Post] = {
    val document = new JsoupBrowser().get(url)
    val title = document >> text(".Post h2")
    val pattern = ".*/r/(.+?)/.*".r

    url match {
      case pattern(subreddit) => Some(Post(url, title, subreddit))
      case _ => None
    }
  }
}