package com.hjcho.itunes_search.data

//
//
//@Singleton
//class CardModelDataSourceFactory @Inject constructor(val api: TodayHouseService) :
//    DataSource.Factory<Int, CardModel>() {
//    val sourceLiveData = MutableLiveData<CardModelDataSource>()
//    override fun create(): DataSource<Int, CardModel> {
//        val source = CardModelDataSource(api)
//        sourceLiveData.postValue(source)
//        return source
//    }
//}
//
//class CardModelDataSource(
//    private val api: TodayHouseService,
//    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
//) :
//    PageKeyedDataSource<Int, CardModel>() {
//
//    val networkState = MutableLiveData<NetworkState>()
//
//    override fun loadInitial(
//        params: LoadInitialParams<Int>,
//        callback: LoadInitialCallback<Int, CardModel>
//    ) {
//        networkState.postValue(NetworkState.LOADING)
//        CoroutineScope(coroutineDispatcher).launch {
//            val data = async { api.getCardFeed(page = 1) }.await()
//            networkState.postValue(NetworkState.LOADED)
//            callback.onResult(data.cards, null, 2)
//        }
//    }
//
//    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CardModel>) {
//
//    }
//
//    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CardModel>) {
//        networkState.postValue(NetworkState.LOADING)
//        CoroutineScope(coroutineDispatcher).launch {
//            val nextPage = params.key + 1
//            val data = async { api.getCardFeed(page = params.key) }.await()
//            networkState.postValue(NetworkState.LOADED)
//            callback.onResult(data.cards, nextPage)
//        }
//    }
//}