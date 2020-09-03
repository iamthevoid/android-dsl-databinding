package iam.thevoid.noxml.demo.ui.mvvm

import iam.thevoid.noxml.demo.data.api.model.CurrencyRate
import iam.thevoid.noxml.demo.ui.mvvm.revolut.CurrencyViewModel
import iam.thevoid.noxml.demo.ui.mvvm.revolut.items.CurrencyHeaderItem
import iam.thevoid.noxml.demo.ui.mvvm.revolut.items.CurrencySimpleItem
import iam.thevoid.noxml.adapterview.ItemBindings

fun currencyBindings(vm : CurrencyViewModel) =
    ItemBindings
        .of(String::class.java) { CurrencyHeaderItem(vm, it) }
        .addBinding(CurrencyRate::class) { CurrencySimpleItem(vm, it) }