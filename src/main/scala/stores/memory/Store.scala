package stores.memory

import scala.collection.immutable.IntMap
import database.Entity
import scala.util.{Try, Success, Failure}
import stores.EntryNotFoundException

class Store[E <: Entity[E]](entries: IntMap[E]) {
  def create(entry: E): Try[(Int, Store[E])] = {
    val id = entries.size + 1
    val newEntries = entries + (id -> entry.withId(id))

    Success(id, Store(newEntries))
  }

  def update(entry: E): Try[Store[E]] = {
    if (!entries.contains(entry.id)) return Failure(new EntryNotFoundException)

    val newEntries = entries.updated(entry.id, entry)
    Success(Store(newEntries))
  }

  def getEntries(): Try[List[E]] = {
    Success(entries.values.toList)
  }
}

object Store {
  def apply[E <: Entity[E]](): Store[E] = {
    apply(IntMap[E]())
  }

  def apply[E <: Entity[E]](entries: IntMap[E]): Store[E] = {
    new Store(entries)
  }
}
