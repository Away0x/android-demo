package com.away0x.lib_network.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity:
 *  - tableName = "table_name":
 *    表名
 *
 *  - indices = {@Index(value="key", unique=false)}
 *    本表索引，用于大量数据的查询优化，unique 有时需要保证数据表的某个或某些字段只有唯一的记录
 *    可以通过设置 @Index 注解的 unique 属性实现
 *
 *  - inheritSuperIndices = false:
 *    值为 true，那么父类中标记的 indices 索引也会算作该表的索引
 *
 *  - primaryKeys = {"key"}:
 *    主键
 *
 *  - foreignKeys = {@ForeignKey(
 *      value = ForeignTable.class,
 *      parentColumns = "foreign_key",
 *      childColumns = "key",
 *      onDelete = 1,
 *      onUpdate = 1,
 *      deferred = false
 *    )}:
 *    外键，一般多用于多表数据查询，可以配置多个外键
 *      - ForeignKey:
 *        用于设置关联表数据更新时所进行的操作，比如可在设置 onDelete=CASCADE，这样当 Cache 表中某个对应记录被删除时，
 *        ForeignTable 表的所有相关记录也会被删除掉
 *        对于 @Insert(OnConflict=REPLACE) 注解，SQLite 是进行 REMOVE 和 REPLACE 操作，而不是 UPDATE 操作，这个可能影响到 foreign key 的约束
 *      - value:
 *        关联查询的表的 Java.class，这里给定 ForeignTable.class
 *      - parentColumns:
 *        与之关联表 ForeignTable 表中的列名
 *      - childColumns:
 *        本表的列名，必须要和 parentColumns 个数一致，这两个可以理解为根据 cache 表中的那个字段去比对 ForeignTable 中的那个字段，认为是有关联关系的数据
 *      - onDelete:
 *        关联表中某条记录被 delete 或 update 时，本表应该怎么做:
 *          - NO_ACTION:   什么也不做
 *          - RESTRICT:    本表跟 parentColumns 有关联的数据会立刻删除或更新，但不允许一对多的关系
 *          - SET_NULL:    本表跟 parentColumns 有关联的数据会被设置为 null
 *          - SET_DEFAULT: 本表跟 parentColumns 有关联的数据会被设置为默认值，也是 null 值
 *          - CASCADE:     本表跟 parentColumns 有关联的数据会一同被删除或更新
 *      - onUpdate:
 *        本表某条记录变更时，与之关联的表该如何做
 *      - deferred:
 *        本表某条记录变更时，与之关联表的数据变更是否要立即执行，还是等待本表事务处理完再来处理关联表。默认是同时处理
 *
 *  - ignoredColumns = {"data"}:
 *    设置本表中不需要映射表的字段
 */
@Entity(tableName = "cache")
data class Cache(
    @PrimaryKey val key: String,
    val data: ByteArray
)