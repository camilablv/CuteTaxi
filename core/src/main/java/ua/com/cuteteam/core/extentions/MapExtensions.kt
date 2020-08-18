package ua.com.cuteteam.core.extentions

fun <K, V> Map<K, V>.findBy(predicate: (Map.Entry<K, V>) -> Boolean): Map.Entry<K, V>? {
    for (entry in this) {
        if (predicate(entry)) return entry
    }
    return null
}

