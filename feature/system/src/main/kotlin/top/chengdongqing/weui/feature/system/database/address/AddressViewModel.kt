package top.chengdongqing.weui.feature.system.database.address

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.Flow
import top.chengdongqing.weui.feature.system.database.address.db.Address
import top.chengdongqing.weui.feature.system.database.address.db.ShopDatabase

@Suppress("UNCHECKED_CAST")
class AddressViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddressViewModel(context) as T
    }
}

class AddressViewModel(context: Context) : ViewModel() {
    private val addressDao by lazy {
        val database = ShopDatabase.getInstance(context)
        database.addressDao()
    }
    val addressList by lazy { addressDao.loadAll() }

    suspend fun insert(address: Address) {
        return addressDao.insert(address)
    }

    suspend fun update(address: Address) {
        return addressDao.update(address)
    }

    suspend fun delete(address: Address) {
        return addressDao.delete(address)
    }

    fun loadById(id: Int): Flow<Address?> {
        return addressDao.loadById(id)
    }
}