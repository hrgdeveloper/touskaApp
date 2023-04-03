package com.example.data.local.room.daos

import androidx.room.*
import com.example.data.local.room.enteties.*
import com.example.data.network.dtos.FloorDto
import com.example.domain.models.Bloc
import com.example.domain.models.Floor
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("delete from Post")
    suspend fun  deletePosts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertPosts(units: List<PostEntity>)


    @Transaction
    suspend fun deleteAndInsert(Posts: List<PostEntity>){
        deletePosts()
        insertPosts(Posts)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertSinglePost(postEntity: PostEntity)

    @Query("select * from Post")
    suspend fun getPosts():List<PostEntity>

    @Query("select * from Post where id = :id")
    suspend  fun getSinglePost(id: Int):PostEntity

    @Update
    suspend fun updatePost(postEntity: PostEntity)

    @Query("delete from Post where id = :post_id")
    suspend fun deletePost(post_id:Int)









}