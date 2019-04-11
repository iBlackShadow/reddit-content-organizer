package stores.memory

import scala.collection.immutable.IntMap
import database.Entity

class Store[E <: Entity[E]](entries: IntMap[E]) {
  def create(entry: E) : Option[(Int, Store[E])] = {
    val id = entries.size + 1
    val newEntries = entries + (id -> entry.withId(id))

    Some(id, Store(newEntries))
  }

  def update(entry: E) : Option[Store[E]] = {
    if(!entries.contains(entry.id)) return None

    val newEntries = entries.updated(entry.id, entry)
    Some(Store(newEntries))
  }

  def getEntries() : Option[List[E]] = {
    Some(entries.values.toList)
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