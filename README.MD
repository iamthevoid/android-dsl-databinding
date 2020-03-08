
<a href="https://bintray.com/beta/#/iamthevoid/maven/android-dsl-databinding"><img src="https://github.com/iamthevoid/android-dsl-databinding/blob/master/lib.svg"></a>

# Android DSL Data Binding

###### Library for bind observable data with view from Android View Framework



### Setter

**Setter** is abstract type. There are its subclasses for RxJava Flowable and Kotlin Coroutines Flow.

Setter assigns with Android View by extension method

```
fun VIEW.addSetter(FLOWABLE_TYPE<DATA>, VIEW.(DATA) -> Unit)
```

So we can add any realization for any data and any view. For example

```kotlin
fun <T : CharSequence> TextView.setText(text: Flowable<T>) =
    addSetter(text) { this.text = it }
```

Under the hood there is ObserveListener object associated with each view. This object store flowables
cache for view and subscribe on data changes when view `onViewAttachedToWindow` and unsubscribe when view `onViewDetachedFromWindow`.

There are many default setters already defined in library (and many will be added in next releases)

You can also easyly add your own setter with `addSetter` method. For example if you're using Glide
for image loading setter will looks like this 

```kotlin
fun ImageView.setImageUrl(image : Flowable<String>) =
    addSetter(image) { url ->
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }
``` 

and then use it in your DSL layout (JetBrains Anko in example, but `addSetter` is extension for
generic type **T : View**, so you can use it anywhere, even in ButterKnifed xml layouts)

```kotlin
frameLayout {
    imageView {
        setImageUrl(vm.user.map { it.avatarUrl })
    }.lparams(matchParent, wrapContent)
}
``` 

### Two Way Binding

There is also support for Two-Way Data Binding

It is representing by Fields and Items.

*Field* is observable property, that emit Optional<T> element when element set to field. Allows 'null'.
*Item* is observable property, that emit T element when element set to field. Allows 'non-null' only.

for both, rx and coroutines provided base types

- Rx(Coroutine)Field
- Rx(Coroutine)Item
- Rx(Coroutine)CharSequence
- Rx(Coroutine)Boolean
- Rx(Coroutine)Long
- Rx(Coroutine)Int
- Rx(Coroutine)Double
- Rx(Coroutine)Float

# Implementation

### RxJava 2

```gradle
implementation 'iam.thevoid.noxml:core:1.0.0-beta01'
implementation 'iam.thevoid.noxml:rx-core:1.0.0-beta01'
```

Optional:
- Recycler View
```gradle
implementation 'iam.thevoid.noxml:core-recycler:1.0.0-beta01'
implementation 'iam.thevoid.noxml:rx-recycler:1.0.0-beta01'
```
- Swipe Refresh Layout
```gradle
implementation 'iam.thevoid.noxml:core-swiperefreshlayot:1.0.0-beta01'
implementation 'iam.thevoid.noxml:rx-swiperefreshlayot:1.0.0-beta01'
```
- Material
```gradle
implementation 'iam.thevoid.noxml:core-design:1.0.0-beta01'
implementation 'iam.thevoid.noxml:rx-design:1.0.0-beta01'
```

### Kotlin Coroutines

```gradle
implementation 'iam.thevoid.noxml:core:1.0.0-beta01'
implementation 'iam.thevoid.noxml:coroutines-core:1.0.0-beta01'
```

Optional:
- Recycler View
```gradle
implementation 'iam.thevoid.noxml:core-recycler:1.0.0-beta01'
implementation 'iam.thevoid.noxml:coroutines-recycler:1.0.0-beta01'
```
- Swipe Refresh Layout
```gradle
implementation 'iam.thevoid.noxml:core-swiperefreshlayot:1.0.0-beta01'
implementation 'iam.thevoid.noxml:coroutines-swiperefreshlayot:1.0.0-beta01'
```
- Material
```gradle
implementation 'iam.thevoid.noxml:core-design:1.0.0-beta01'
implementation 'iam.thevoid.noxml:coroutines-design:1.0.0-beta01'
```
