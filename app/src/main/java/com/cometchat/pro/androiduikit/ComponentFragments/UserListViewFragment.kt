package com.cometchat.pro.androiduikit.ComponentFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import com.cometchat.pro.androiduikit.R
import com.cometchat.pro.androiduikit.databinding.FragmentUserListBinding
import com.cometchat.pro.constants.CometChatConstants
import com.cometchat.pro.core.CometChat.CallbackListener
import com.cometchat.pro.core.UsersRequest
import com.cometchat.pro.core.UsersRequest.UsersRequestBuilder
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.User
import com.facebook.shimmer.ShimmerFrameLayout
import constant.StringContract
import listeners.OnItemClickListener
import screen.messagelist.CometChatMessageListActivity

class UserListViewFragment : Fragment() {
    var usersRequest: UsersRequest? = null
    var userListBinding: FragmentUserListBinding? = null
    var observableArrayList = ObservableArrayList<User>()
    var shimmerList: ShimmerFrameLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        userListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false)
        users
        userListBinding!!.setUserList(observableArrayList)
        userListBinding!!.cometchatUserList.setItemClickListener(object : OnItemClickListener<User?>() {
            override fun OnItemClick(t: Any, position: Int) {
                val user = t as User
                val intent = Intent(context, CometChatMessageListActivity::class.java)
                intent.putExtra(StringContract.IntentStrings.TYPE, CometChatConstants.RECEIVER_TYPE_USER)
                intent.putExtra(StringContract.IntentStrings.NAME, user.name)
                intent.putExtra(StringContract.IntentStrings.UID, user.uid)
                intent.putExtra(StringContract.IntentStrings.AVATAR, user.avatar)
                intent.putExtra(StringContract.IntentStrings.STATUS, user.status)
                startActivity(intent)
            }
        })
        return userListBinding!!.getRoot()
    }

    private val users: Unit
        private get() {
            if (usersRequest == null) {
                usersRequest = UsersRequestBuilder().setLimit(30).build()
            }
            usersRequest!!.fetchNext(object : CallbackListener<List<User?>?>() {
                override fun onSuccess(users: List<User?>?) {
                    userListBinding!!.contactShimmer.stopShimmer()
                    userListBinding!!.contactShimmer.visibility = View.GONE
                    observableArrayList.addAll(users!!)
                }

                override fun onError(e: CometChatException) {}
            })
        }
}