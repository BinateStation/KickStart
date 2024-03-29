# KickStart

Add dependency by update your module gradle file as like below

```
repositories {
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/BinateStation/KickStart")
        credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") ?: System.getenv("TOKEN")
        }
    }
}

dependencies {
  implementation 'com.binatestation.android:kickoff:1.3.1'
}
```

You can use your github user id or username as username and you can create a private access token
from your profile, specific to read package registry.


------------------------------------------------------------------------------------------

This is a library which can be used for avoiding boiler plate code in the RecyclerView

## What's New

1. DataBinding issue fixes
2. Progress Dialog context issue fixes
3. Now we have paging with DB + Network
4. com.binatestation.android.kickoff.utils.fragments.ImageCarouselFragment
5. com.binatestation.android.kickoff.utils.fragments.PageSliderWithIndicatorFragment
6. com.binatestation.android.kickoff.utils.fragments.SwipeListSearchFragment
7. com.binatestation.android.kickoff.utils.fragments.PagedSwipeListSearchFragment
8. Now you can customize EmptyStateModel in PagedRecyclerViewAdapter 
9. Network State EmptyStateModel in PagedRecyclerViewAdapter 
10. Html Support in Empty State Text View
11. Error Object key in get callback
12. WebviewDialogFragment is now open. you can extend it
13. Now you can set click listener for child recycler view adapter too.

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

## Set click listener for child recycler view

```
class AlbumViewHolder(
    private val adapterAlbumBinding: AdapterAlbumBinding
) : BaseViewHolder(adapterAlbumBinding.root) { 
    // .......
    private val childRecyclerViewAdapter = RecyclerViewAdapter()
    // .......

    override fun getChildAdapter(): RecyclerViewAdapter? {
        return childRecyclerViewAdapter
    }
// .......
}
```

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
