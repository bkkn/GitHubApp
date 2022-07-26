package me.bkkn.dil

import kotlin.reflect.KClass

object Di {
    private val dependenciesHolder = HashMap<KClass<*>, DependencyFactory<*>>()
    fun <T : Any> get(clazz: KClass<T>): T {
        val dependencyFactory = dependenciesHolder[clazz]
        if (dependencyFactory != null) {
            return dependencyFactory.get() as T
        } else throw IllegalAccessException("no such Key in map")
    }

    fun <T : Any> add(clazz: KClass<T>, dependencyFactory: DependencyFactory<T>) {
        dependenciesHolder[clazz] = dependencyFactory
    }

    inline fun <reified T : Any> add(dependencyFactory: DependencyFactory<T>) {
        add(T::class, dependencyFactory)
    }
}

inline fun <reified T : Any> get(): T {
    return Di.get(T::class)
}

inline fun <reified T : Any> inject() = lazy {
    get<T>()
}

abstract class DependencyFactory<T : Any>(protected val creator: () -> Any) {
    abstract fun get(): Any
}

class Singleton<T : Any>(creator: () -> Any) : DependencyFactory<T>(creator) {
    private val dependency: Any by lazy { creator.invoke() }
    override fun get(): Any = dependency
}

class Factory<T : Any>(creator: () -> Any) : DependencyFactory<T>(creator) {
    override fun get(): Any = creator()
}

inline fun <reified T : Any> singleton(noinline creator: () -> T): DependencyFactory<T> {
    return Singleton<T>(creator)
}

inline fun <reified T : Any> factory(noinline creator: () -> T): DependencyFactory<T> {
    return Factory<T>(creator)
}

class Module(private val block: Module.() -> Unit) {

    fun install() {
        block()
    }

    inline fun <reified T : Any> singleton(noinline creator: () -> T) {
        Di.add(Singleton<T>(creator))
    }

    inline fun <reified T : Any> factory(noinline creator: () -> T) {
        Di.add(Factory<T>(creator))
    }
}