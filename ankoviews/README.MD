Library provides Anko adapter for 'noxml-mvvm' lib, such as

AnkoMvvmActivity
AnkoMvvmBottomSheetDialogFragment
AnkoMvvmDialogFragment
AnkoMvvmFragment


Also it provides views and constants extends Anko native funcs and constants :) (List of them will be extended when new
functions will be needed)

## Constants

<details><summary>actionBarHeight</summary>
<p>

#### Attr dimen of action bar

```kotlin
val View.actionBarHeight : Int
```

</p>
</details>

## Views

<details><summary>SparseConstraintLayout</summary>
<p>

#### 'Sparse' constraint layout

Let users declare constraints on each view separately, not in one 'applyConstraintSet' fun. Example:

```kotlin
    sparseConstraintLayout {

        editText {
            id = R.id.et
            hint = "Enter something"
            onTextChanges(viewModel<PageViewModel>().changes)
        }.lparams(matchConstraint, wrapContent)
            .constraint {
                connect(
                    TOP to TOP of PARENT_ID,
                    START to START of PARENT_ID,
                    END to END of PARENT_ID
                )
            }

        textView {
            id = R.id.text1
            text = "Entered"
        }.constraint {
            connect(
                TOP to BOTTOM of R.id.et,
                START to START of PARENT_ID,
                END to START of R.id.text2,
                BOTTOM to BOTTOM of PARENT_ID
            )

            horizontalChainStyle = ConstraintSet.CHAIN_SPREAD
            horizontalWeight = 1f
        }

        textView {
            id = R.id.text2
            textSize = dip(16).toFloat()
            setText(viewModel<PageViewModel>().changes.observe { it.elem?.s?.toString().safe })
        }.constraint {
            connect(
                TOP to BOTTOM of R.id.et,
                START to END of R.id.text1,
                END to END of PARENT_ID,
                BOTTOM to BOTTOM of PARENT_ID
            )

            horizontalChainStyle = ConstraintSet.CHAIN_SPREAD
            horizontalWeight = 1f
        }
    }
```

</p>
</details>
