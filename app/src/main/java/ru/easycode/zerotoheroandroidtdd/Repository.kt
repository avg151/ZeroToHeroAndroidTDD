package ru.easycode.zerotoheroandroidtdd

interface Repository {

    interface Read {
        fun list(): List<Item>
    }

    interface Add {
        fun add(value: String): Long
    }

    interface Delete {
        fun item(id: Long): Item
        fun delete(id: Long)
    }

    interface Update {
        fun update(id: Long, newText: String)
    }

    interface Change : Delete, Update

    interface All : Read, Add, Change

    class Base(
        private val dataSource: ItemsDao,
        private val now: Now
    ) : All {
        override fun list(): List<Item> = dataSource.list().map { Item(it.id, it.text) }

        override fun add(value: String): Long {
            val id = now.nowMillis()
            dataSource.add(ItemCache(id = id, text = value))
            return id
        }

        override fun item(id: Long): Item {
            val cache = dataSource.item(id)
            return Item(cache.id, cache.text)
        }

        override fun delete(id: Long) {
            dataSource.delete(id)
        }

        override fun update(id: Long, newText: String) {
            dataSource.add(ItemCache(id, newText))
        }
    }
}
