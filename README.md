# KickStart

Add dependency by update your module gradle file as like below

```
repositories {
     maven {
                url 'https://gitlab.com/api/v4/projects/8748353/packages/maven'
                name "GitLab"
                credentials(HttpHeaderCredentials) {
                    name = 'Private-Token'
                    value = gitLabPrivateToken
                }
                authentication {
                    header(HttpHeaderAuthentication)
                }
     }
}

dependencies {
  implementation 'com.binatestation.android:kickoff:1.2.13'
}
```

This is a library which can be used for avoiding boiler plate code in the RecyclerView

## What's New
1. DataBinding issue fixes
1. Progress Dialog context issue fixes
1. Now we have paging with DB + Network
1. com.binatestation.android.kickoff.utils.fragments.ImageCarouselFragment
1. com.binatestation.android.kickoff.utils.fragments.PageSliderWithIndicatorFragment
1. com.binatestation.android.kickoff.utils.fragments.SwipeListSearchFragment
1. com.binatestation.android.kickoff.utils.fragments.PagedSwipeListSearchFragment
1. Now you can customize EmptyStateModel in PagedRecyclerViewAdapter 
1. Network State EmptyStateModel in PagedRecyclerViewAdapter 
1. Html Support in Empty State Text View
1. Error Object key in get callback
1. WebviewDialogFragment is now open. you can extend it

## Prerequisite

1. Kotlin
1. DataBinding
1. MVVM

## TODO

you can use this by just Extending the Fragments listed bellow

* com.binatestation.android.kickoff.utils.fragments.ListFragment
* com.binatestation.android.kickoff.utils.fragments.SwipeListFragment
* com.binatestation.android.kickoff.utils.fragments.SwipeListAddFragment
* com.binatestation.android.kickoff.utils.fragments.SwipeListAddSearchFragment
* com.binatestation.android.kickoff.utils.fragments.SwipeListSearchFragment
* com.binatestation.android.kickoff.utils.fragments.PagedListFragment
* com.binatestation.android.kickoff.utils.fragments.PagedSwipeListFragment
* com.binatestation.android.kickoff.utils.fragments.PagedSwipeListAddFragment
* com.binatestation.android.kickoff.utils.fragments.PagedSwipeListAddSearchFragment
* com.binatestation.android.kickoff.utils.fragments.PagedSwipeListSearchFragment
* com.binatestation.android.kickoff.utils.fragments.PageSliderWithIndicatorFragment
* com.binatestation.android.kickoff.utils.fragments.ImageCarouselFragment

## Example Snippets


### RecyclerView without Paging

```
class AlbumFragment : ListFragment() {

   // .......

 override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.itemViewTypeModels.add(
            ItemViewTypeModel(
                AlbumModel::class.java, // The Data Model that have data to bind in view holder xml
                AlbumViewHolder::class.java, // View Holder class type
                AlbumViewHolder.LAYOUT, // Layout Id to use in View Holder
                AdapterAlbumBinding::class.java // Auto generated Binding class type
            )
        )
        albumViewModel.albums.observe(viewLifecycleOwner, Observer { it ->
            it.data?.let {
                adapter.setTypedData(it)
            }
        })
    }
    
    // ......
```

### RecyclerView with Paging

```
class UOMListFragment : PagedSwipeListAddSearchFragment<UOMModel>(
    UOMModel.COMPARATOR
) {

 // ....
 
     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setOnRefreshListener { viewModel.refresh() }
        // ....
    }

    private fun initAdapter() {
        adapter.itemViewTypeModels.add(
            ItemViewTypeModel(
                UOMModel::class.java,
                UOMViewHolder::class.java,
                UOMViewHolder.LAYOUT,
                AdapterUomBinding::class.java
            )
        )
        viewModel.uoms.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it) 
        })
        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            adapter.setNetworkState(it)
        })
        viewModel.refreshState.observe(viewLifecycleOwner, Observer {
            getSwipeRefreshLayout()?.isRefreshing = it == NetworkState.LOADING
        })
    }

// ....
```

###### In Repository Class

```
class UOMRepository(private val uomApi: UOMApi) : BasePagedRepository<UOMModel>() {

    fun getAll(pageIndex: Int, pageSize: Int) =
        super.getAll(pageIndex, pageSize) { index, size, apiCallBack ->
            uomApi.getAll(pageIndex = index, pageSize = size)
                .enqueue(object : retrofit2.Callback<List<UOMModel>> {
                    override fun onFailure(call: Call<List<UOMModel>>, throwable: Throwable) {
                        apiCallBack(ApiResponse.create(throwable))
                    }

                    override fun onResponse(
                        call: Call<List<UOMModel>>,
                        response: Response<List<UOMModel>>
                    ) {
                        apiCallBack(ApiResponse.create(call, response))
                    }
                })
        }
        
        // .....
}
```



