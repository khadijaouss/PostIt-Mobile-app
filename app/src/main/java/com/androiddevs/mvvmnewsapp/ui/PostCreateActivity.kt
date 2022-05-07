package com.androiddevs.mvvmnewsapp.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.data.CreatePost
import com.androiddevs.mvvmnewsapp.repository.PostRepository
import com.androiddevs.mvvmnewsapp.util.RealPathUtil
import com.androiddevs.mvvmnewsapp.util.RealPathUtil.getFilePath
import com.androiddevs.mvvmnewsapp.util.URIPathHelper
import kotlinx.android.synthetic.main.post_create.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Url
import java.io.File
import java.io.FileReader
import java.net.URI

//class pour créer un post
class PostCreateActivity: AppCompatActivity()  {
    private lateinit var buttonCreate: Button
    private lateinit var text: EditText
    private lateinit var tag1: EditText
    private lateinit var tag2: EditText
    private lateinit var tag3: EditText
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var title: EditText
    private lateinit var picture: ImageView
    private lateinit var image: ImageView
    private lateinit var viewModel: MainViewModel
    private var  CODE_MULTIPLE_IMG_GALLERY = 2
    private var CODE_IMG_GALLERY=1
    var filePath1:String?=null
    var filePath2:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_create)

        buttonCreate=findViewById<Button>(R.id.buttonCreate)
        picture=findViewById<ImageView>(R.id.picture)
        image=findViewById<ImageView>(R.id.image)
        firstName=findViewById<EditText>(R.id.firstName )
        lastName=findViewById<EditText>(R.id.lastName)
        title=findViewById<EditText>(R.id.title)
        text=findViewById<EditText>(R.id.text)
        tag1=findViewById<EditText>(R.id.tag1)
        tag2=findViewById<EditText>(R.id.tag2)
        tag3=findViewById<EditText>(R.id.tag3)
        //une fois on clique sur le bouton Post,la fonction createPostData() sera appelée
        buttonCreate.setOnClickListener {
            createPostData(firstName.text.toString(),lastName.text.toString(),title.text.toString(),text.text.toString(),tag1.text.toString(),tag2.text.toString(),tag3.text.toString())

        }
        //picture picker from gallery
        picture.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, CODE_IMG_GALLERY)
            val context:Context=PostCreateActivity()
            filePath2 = intent.getFilePath(context)
        }
        //image picker from gallery
        image.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
            intent.action=Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, CODE_MULTIPLE_IMG_GALLERY)
            val context:Context=PostCreateActivity()
            filePath1 = intent.getFilePath(context)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_IMG_GALLERY && resultCode == RESULT_OK ) {
            val uri1=data!!.data
            picture.setImageURI(uri1)

        }
        else{
            val uri2=data!!.data
            image.setImageURI(uri2)
        }

    }




    private fun createPostData(firstName:String,lastName:String,title:String,texte:String,tag1:String,tag2:String,tag3:String) {

        val picture="https://img.dummyapi.io/photo-1510414696678-2415ad8474aa.jpg"
        val image="https://randomuser.me/api/portraits/med/men/40.jpg"
        val postRepository = PostRepository()
        val viewModelFactory = MainViewModelProviderFactory(postRepository)
        val createPost=CreatePost(image,firstName,lastName,title,texte,picture,tag1,tag2,tag3)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.createPost(createPost)
        this.startActivity(intent)


    }
}