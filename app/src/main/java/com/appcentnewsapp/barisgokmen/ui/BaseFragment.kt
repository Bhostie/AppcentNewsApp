package com.appcentnewsapp.barisgokmen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T: ViewBinding> : Fragment(){

    private var _binding: T? = null
    val binding: T? get() = _binding

    abstract fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): T

    abstract fun initialize()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return _binding?.root
    }

}