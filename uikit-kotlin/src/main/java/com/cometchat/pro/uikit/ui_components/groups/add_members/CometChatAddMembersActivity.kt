package com.cometchat.pro.uikit.ui_components.groups.add_members

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cometchat.pro.uikit.R
import com.cometchat.pro.uikit.ui_resources.constants.UIKitContracts

class CometChatAddMembersActivity : AppCompatActivity() {
    private var fragment: Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen)
        handleIntent()
    }

    private fun handleIntent() {
        if (intent != null) {
            val bundle = Bundle()
            fragment = CometChatAddMembers()
            bundle.putString(UIKitContracts.IntentStrings.GUID, intent.getStringExtra(UIKitContracts.IntentStrings.GUID))
            bundle.putString(UIKitContracts.IntentStrings.GROUP_NAME, intent.getStringExtra(UIKitContracts.IntentStrings.GROUP_NAME))
            bundle.putStringArrayList(UIKitContracts.IntentStrings.GROUP_MEMBER, intent.getStringArrayListExtra(UIKitContracts.IntentStrings.GROUP_MEMBER))
            fragment!!.setArguments(bundle)
            supportFragmentManager.beginTransaction().replace(R.id.frame_fragment, fragment!!).commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}