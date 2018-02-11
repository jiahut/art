package reactivex

import io.reactivex.Flowable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

fun main(args: Array<String>) {

    val list = listOf("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
    list.toObservable()
            .filter { it.length >= 5 }
            .subscribeBy(
                    onNext = { println(it) },
                    onComplete = { println("done.") },
                    onError = { it.printStackTrace() }
            )

    println("-----")
    Flowable.just("hello world").subscribe(::println)

    println("-----")
    Flowable.fromCallable {
        Thread.sleep(2)
        return@fromCallable "Done"
    }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .subscribe(::println, Throwable::printStackTrace)
    Thread.sleep(20)

    println("-----")
    Flowable.range(1, 10)
            .observeOn(Schedulers.computation())
            .map { it * it }
            .blockingSubscribe(::println)

    Flowable.range(1, 10)
            .flatMap {
                Flowable.just(it)
                        .subscribeOn(Schedulers.computation())
                        .map { it * it }
            }
            .blockingSubscribe(::println)

    println("----")
    Flowable.range(1, 10)
            .parallel()
            .runOn(Schedulers.computation())
            .map { it * it }
            .sequential()
            .blockingSubscribe(::println)


    println("-----")
    Flowable.just("one", "two", "three", "four")
            .subscribeBy(
                    onNext = ::println,
                    onError = Throwable::printStackTrace,
                    onComplete = { println("done.") }
            )
    println("-----")
    Flowable.fromArray("one", "two")
            .subscribe(::println, { it.printStackTrace() }, { println("done") })
}