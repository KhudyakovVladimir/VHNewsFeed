package com.khudyakovvladimir.vhnewsfeed.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.khudyakovvladimir.vhnewsfeed.R

class TopicFragment: Fragment() {

    lateinit var editText: EditText
    lateinit var button: Button

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.topic_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editText = view.findViewById(R.id.editTextTopic)
        button = view.findViewById(R.id.search)

        button.setOnClickListener {
            val topic = editText.text.toString()
            val bundle = Bundle()
            bundle.putString("topic", topic)
            Log.d("TAG", "TopicFragment - onViewCreated() - topic = $topic")
            findNavController().navigate(R.id.feedFragment, bundle)
        }
    }
}