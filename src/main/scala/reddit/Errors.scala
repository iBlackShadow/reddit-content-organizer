package reddit

class SubredditNotFoundException extends Exception
class InvalidPostUrlException(message: String) extends Exception(message: String)